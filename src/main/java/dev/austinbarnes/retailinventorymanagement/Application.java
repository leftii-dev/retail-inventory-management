package dev.austinbarnes.retailinventorymanagement;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeMapper;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import dev.austinbarnes.retailinventorymanagement.entitycode.entity.CodeEntity;
import dev.austinbarnes.retailinventorymanagement.entitycode.repo.CodeEntityRepository;
import dev.austinbarnes.retailinventorymanagement.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	CommandLineRunner runner(EmployeeRepository employeeRepository, RoleRepository roleRepository, UserRepository userRepository,
//							 CodeEntityRepository codeEntityRepository) {
//		return args -> {
//		};
//	}
}
