package dev.austinbarnes.retailinventorymanagement.config;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Permission;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import dev.austinbarnes.retailinventorymanagement.employee.repo.PermissionRepository;
import dev.austinbarnes.retailinventorymanagement.entitycode.entity.CodeEntity;
import dev.austinbarnes.retailinventorymanagement.entitycode.repo.CodeEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final CodeEntityRepository codeEntityRepository;

    @Value("${system.employee.id}")
    private UUID systemEmployeeId;
    @Value("${system.user.email}")
    private String systemUserEmail;
    @Value("${initial.admin.email:}")
    private String initialAdminEmail;
    @Value("${initial.admin.password:}")
    private String initialAdminPassword;

    @Override
    @Transactional
    public void run(String... args) {
        createSystemUserAndEmployee();
        createPermissions();
        createRoles();
        createCodeGeneratorIndices();
        createInitialAdminUser();
    }

    private void createInitialAdminUser() {
        if(initialAdminEmail == null || initialAdminEmail.isBlank()) {
            return;
        }

        if(userRepository.findByEmail(initialAdminEmail).isPresent()) {
            return;
        }
         log.info("Seeding initial admin user...");

        String rawPassword;
        boolean isRandom = false;

        if(initialAdminPassword != null && !initialAdminPassword.isEmpty()) {
            rawPassword = initialAdminPassword;
        } else {
            rawPassword = UUID.randomUUID().toString().substring(0, 8);
            isRandom = true;
        }

        // Create initial Admin user
        User adminUser = new User();
        adminUser.setEmail(initialAdminEmail);
        adminUser.setPassword(passwordEncoder.encode(rawPassword));
        adminUser.setName("Initial Admin");
        adminUser.setActive(true);
        adminUser.setEnabled(true);
        adminUser.setAccountNonLocked(true);

        // Get and assign ADMIN role
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN role not found during seeding"));
        adminUser.setRoles(new HashSet<>(Set.of(adminRole)));

        User savedAdminUser = userRepository.save(adminUser);

        // Create ADMIN employee for ADMIN user account
        Employee adminEmployee = new Employee();

        adminEmployee.setUser(savedAdminUser);
        adminEmployee.setEmail(initialAdminEmail);
        adminEmployee.setNameFirst("Super");
        adminEmployee.setNameLast("Admin");
        adminEmployee.setActive(true);
        adminEmployee.setPermissions(getAllPermissions());
        adminEmployee.setEmployeeCode("EMP-100001");

        employeeRepository.save(adminEmployee);

        log.info("-------------------------------------------------------------------");
        log.info("ADMIN ACCOUNT CREATED SUCCESSFULLY");
        log.info("Email:     {}", initialAdminEmail);
        if(isRandom) {
            log.info("Password:  {}   <-- COPY THIS NOW, IT WILL NOT BE SHOWN AGAIN", rawPassword);
        } else {
            log.info("Password: [Provided via Configuration]");
        }
        log.info("-------------------------------------------------------------------");
    }

    private Set<Permission> getAllPermissions() {
        return new HashSet<>(permissionRepository.findAll());
    }

    private void createCodeGeneratorIndices() {
        List<CodeEntity> codeEntities = List.of(
                new CodeEntity("warehouselocation", 1),
                new CodeEntity("product", 1),
                new CodeEntity("receivingvoucher", 1),
                new CodeEntity("category", 1),
                new CodeEntity("vendor", 1),
                new CodeEntity("purchaseorder", 1),
                new CodeEntity("transfer", 1),
                new CodeEntity("retaillocation", 1),
                new CodeEntity("employee", 2)
        );
        codeEntities.forEach(code -> {
            if(codeEntityRepository.findByName(code.getName()).isEmpty()){
                codeEntityRepository.save(code);
            }
        });
    }

    private void createRoles() {
            List<Role> roles = List.of(
                    new Role("ADMIN"),
                    new Role("SHOPPER"),
                    new Role("MANAGER"),
                    new Role("EMPLOYEE")
            );

            roles.forEach(role -> {
                if(roleRepository.findByName(role.getName()).isEmpty()){
                    roleRepository.save(role);
                }
            });
    }

    private void createPermissions() {
            List<Permission> permissions = List.of(
                    new Permission("WRITE_PO", "Write permissions for purchase order records"),
                    new Permission("WRITE_INVENTORY", "Write permissions for inventory records"),
                    new Permission("WRITE_RV", "Write permissions for receiving voucher records"),
                    new Permission("WRITE_STATUS", "Write permissions for status types"),
                    new Permission("WRITE_TRANSFER", "Write permissions for transfer slip records"),
                    new Permission("WRITE_VENDOR", "Write permissions for vendor records"),
                    new Permission("WRITE_LOCATION", "Write permissions for location records"),
                    new Permission("WRITE_PRODUCT", "Write permissions for product records"),
                    new Permission("WRITE_DISCOUNT", "Write permissions for discount records"),
                    new Permission("READ_PO", "Read permissions for purchase order records"),
                    new Permission("READ_RV", "Read permissions for receiving voucher records"),
                    new Permission("READ_STATUS", "Read permissions for status type records"),
                    new Permission("READ_TRANSFER", "Read permissions for transfer slip records"),
                    new Permission("READ_VENDOR", "Read permissions for vendor records")
                );
            permissions.forEach(permission -> {
                if(permissionRepository.findByName(permission.getName()).isEmpty()){
                    permissionRepository.save(permission);
                }
            });
    }

    private void createSystemUserAndEmployee() {
        User savedSystemUser = userRepository.findByEmail(systemUserEmail)
                .orElseGet(() -> {
                    User sysUser = new User();
                    sysUser.setEnabled(false);
                    sysUser.setName("SYSTEM");
                    sysUser.setEmail(systemUserEmail);
                    sysUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    sysUser.setAccountNonLocked(false);
                    return userRepository.save(sysUser);
                });

        if(employeeRepository.findById(systemEmployeeId).isEmpty()) {
            Employee sysEmployee = new Employee();
            sysEmployee.setId(systemEmployeeId);
            sysEmployee.setUser(savedSystemUser);
            sysEmployee.setNameFirst("SYSTEM");
            sysEmployee.setNameLast("SYSTEM");
            sysEmployee.setEmail(systemUserEmail);
            sysEmployee.setEmployeeCode("EMP-100000");
            employeeRepository.save(sysEmployee);
        }
    }
}
