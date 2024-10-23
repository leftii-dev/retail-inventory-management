package dev.austinbarnes.retailinventorymanagement.auth;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmployeeCode) throws UsernameNotFoundException {
        User user;
        String loginIdentifier;

        if(isEmail(usernameOrEmployeeCode)) {
            user = userRepository.findByEmail(usernameOrEmployeeCode)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + usernameOrEmployeeCode));
            loginIdentifier = user.getEmail();
        } else {
            // If not an email, treat as employee code - Ad prefix for searching with repository
            // employee should only use the numbers in their employeeCode
            Employee employee = employeeRepository.findByEmployeeCode("EMP-" + usernameOrEmployeeCode)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with employee code " + "EMP-" + usernameOrEmployeeCode));
            loginIdentifier = employee.getEmployeeCode();
            user = employee.getUser();
        }

        return new CustomUserDetails(user, loginIdentifier);
    }

    private boolean isEmail(String input) {
        return input.contains("@");
    }
}
