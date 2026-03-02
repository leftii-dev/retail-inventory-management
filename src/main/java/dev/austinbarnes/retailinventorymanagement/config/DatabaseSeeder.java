package dev.austinbarnes.retailinventorymanagement.config;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Permission;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import dev.austinbarnes.retailinventorymanagement.employee.repo.PermissionRepository;
import dev.austinbarnes.retailinventorymanagement.entitycode.CodeGenerator;
import dev.austinbarnes.retailinventorymanagement.entitycode.entity.CodeEntity;
import dev.austinbarnes.retailinventorymanagement.entitycode.repo.CodeEntityRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.StatusRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.VendorRepository;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationType;
import dev.austinbarnes.retailinventorymanagement.location.entity.WarehouseLocation;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationRepository;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationTypeRepository;
import dev.austinbarnes.retailinventorymanagement.location.repo.WarehouseLocationRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

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
    private final EntityManager entityManager; // Used to persist with custom UUID
    private final StatusRepository statusRepository;
    private final VendorRepository vendorRepository;
    private final CodeGenerator codeGenerator;
    private final LocationTypeRepository locationTypeRepository;
    private final LocationRepository locationRepository;
    private final WarehouseLocationRepository warehouseLocationRepository;

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
        createDefaultStatuses();
        createFillerVendors();
        createDefaultWarehouseLocation();
    }

    private void createInitialAdminUser() {
        if (initialAdminEmail == null || initialAdminEmail.isBlank()) {
            return;
        }

        if (userRepository.findByEmail(initialAdminEmail).isPresent()) {
            return;
        }
        log.info("Seeding initial admin user...");

        String rawPassword;
        boolean isRandom = false;

        if (initialAdminPassword != null && !initialAdminPassword.isEmpty()) {
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
        if (isRandom) {
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
            if (codeEntityRepository.findByName(code.getName()).isEmpty()) {
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
            if (roleRepository.findByName(role.getName()).isEmpty()) {
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
            if (permissionRepository.findByName(permission.getName()).isEmpty()) {
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

        if (employeeRepository.findById(systemEmployeeId).isEmpty()) {
            String sql = "INSERT INTO employee " +
                    "(id, created_at, modified_at, created_by, modified_by, active, " +
                    "email, employee_code, is_current_employee, name_first, name_last, user_id) " +
                    "VALUES (:id, :now, :now, :auditId, :auditId, true, " +
                    ":email, :code, true, :first, :last, :userId)";

            entityManager.createNativeQuery(sql)
                    .setParameter("id", systemEmployeeId)
                    .setParameter("now", Instant.now())
                    .setParameter("auditId", systemEmployeeId)
                    .setParameter("email", systemUserEmail)
                    .setParameter("code", "EMP-100000")
                    .setParameter("first", "SYSTEM")
                    .setParameter("last", "SYSTEM")
                    .setParameter("userId", savedSystemUser.getId())
                    .executeUpdate();
        }
    }

    private void createDefaultStatuses() {
        Map<String, String> defaultStatuses = Map.of("PENDING", "Item has been processed and is pending.",
                "OPEN", "Item has been started, but not yet processed.",
                "CLOSED", "Item has been closed and can no longer be processed.",
                "APPROVED", "Item has been approved and is awaiting processing.",
                "RECEIVED", "Item has been received and is awaiting processing.",
                "CANCELLED", "Item has been cancelled and will not be processed.",
                "DRAFT", "Item is in draft form and has not yet been submitted for processing.",
                "COMPLETED", "Item has been completed and processed successfully.");
        defaultStatuses.forEach((statusName, statusDescription) -> {
            if (statusRepository.findByName(statusName).isEmpty()) {
                statusRepository.save(new Status(statusName, statusDescription));
            }
        });
    }

    private void createFillerVendors() {
        List<Vendor> vendors = List.of(
                new Vendor(codeGenerator.generateVendorCode(), "Acme Corporation", "123 ACME Way", null, "Anytown", "NY", "12345", "John Doe", "5551234567", "john.doe@acme.biz"),
                new Vendor(codeGenerator.generateVendorCode(), "Peebody Electronics", "999 Belt Way", null, "CityDale", "CA", "95432", "Katie Fray", "9991234567", "katie.fray@pb.xyz"),
                new Vendor(codeGenerator.generateVendorCode(), "Esther Corp", "3955 Mckinley Parkway", null, "Townsville", "AL", "45682", "Jerry Stone", "1231234567", "jerry.stone@esther.dev")
        );
        vendors.forEach(vendor -> {
            if(vendorRepository.findByName(vendor.getName()).isEmpty()) {
                vendorRepository.save(vendor);
            }
        });
    }

    private void createDefaultWarehouseLocation() {
        if (locationRepository.findByName("Default Warehouse").isPresent()) {
            return;
        }
        log.info("Seeding default warehouse location...");
        LocationType warehouseType = locationTypeRepository.findByName("WAREHOUSE")
                .orElseGet(() -> locationTypeRepository.save(new LocationType("WAREHOUSE")));
        Location location = new Location("Default Warehouse", warehouseType);
        Location savedLocation = locationRepository.save(location);
        WarehouseLocation warehouse = new WarehouseLocation(
                codeGenerator.generateWarehouseLocationCode(), savedLocation);
        warehouseLocationRepository.save(warehouse);
    }
}