package dev.austinbarnes.retailinventorymanagement;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
@EnableAsync
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	 CommandLineRunner runner(CodeEntityRepository codeEntityRepository, RoleRepository roleRepository, UserRepository userRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, CodeGenerator codeGenerator) {
//		return args -> {
//			codeEntityRepository.saveAllAndFlush(Set.of(
//					new CodeEntity("employee", 1),
//					new CodeEntity("purchaseorder", 1),
//					new CodeEntity("receivingvoucher", 1),
//					new CodeEntity("transfer", 1),
//					new CodeEntity("vendor", 1),
//					new CodeEntity("retaillocation", 1),
//					new CodeEntity("warehouselocation", 1),
//					new CodeEntity("category", 1),
//					new CodeEntity("product", 1)
//			));
//
////			Role first
//			Role role = new Role();
//			role.setCreatedBy(null);
//			role.setModifiedBy(null);
//			role.setName("ADMIN");
//			Role role1 = new Role();
//			role1.setCreatedBy(null);
//			role1.setModifiedBy(null);
//			role1.setName("MANAGER");
//			Role role2 = new Role();
//			role2.setCreatedBy(null);
//			role2.setModifiedBy(null);
//			role2.setName("EMPLOYEE");
//			Role role3 = new Role();
//			role3.setCreatedBy(null);
//			role3.setModifiedBy(null);
//			role3.setName("SHOPPER");
//			roleRepository.saveAllAndFlush(Set.of(role, role1, role2, role3));
//			Role sysRole = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("System Role not found"));
//
////			User second
//			User sysUser = new User();
//			sysUser.setCreatedBy(null);
//			sysUser.setModifiedBy(null);
//			sysUser.setEmail("inventory_management@austinbarnes.dev");
//			sysUser.setName("System");
//			sysUser.setPassword(passwordEncoder.encode("HookRat94561!"));
//			sysUser.setOauthProvider(null);
//			sysUser.setOauthProviderId(null);
//			sysUser.setPictureUrl(null);
//			sysUser.setRoles(Set.of(sysRole));
//			User sysUserUser = userRepository.saveAndFlush(sysUser);
//
////			Employee third
//			Employee sys = new Employee();
//			sys.setCreatedBy(null);
//			sys.setModifiedBy(null);
//			sys.setUser(sysUserUser);
//			sys.setNameFirst("System");
//			sys.setNameLast("System");
//			sys.setPhone(null);
//			sys.setEmail("inventory_management@austinbarnes.dev");
//			sys.setDateOfBirth(null);
//			sys.setEmployeeCode(codeGenerator.generateEmployeeCode());
//			employeeRepository.saveAndFlush(sys);
//
//		};
//	}
}
