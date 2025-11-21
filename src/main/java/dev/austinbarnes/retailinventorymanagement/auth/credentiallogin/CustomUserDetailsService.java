package dev.austinbarnes.retailinventorymanagement.auth.credentiallogin;

import dev.austinbarnes.retailinventorymanagement.auth.CustomUserPrincipal;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService implements UserDetailsService to load user-specific
 * data during authentication. It retrieves user information from the database
 * based on the provided username or employee code.
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * Loads user details by username or employee code.
     * If the input contains '@', it is treated as an email.
     * Otherwise, it is treated as an employee code.
     *
     * @param usernameOrEmployeeCode the username or employee code
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmployeeCode) throws UsernameNotFoundException {
        User user;
        String loginIdentifier;

        if(isEmail(usernameOrEmployeeCode)) {
            user = userRepository.findByEmail(usernameOrEmployeeCode)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + usernameOrEmployeeCode));
            loginIdentifier = user.getEmail();
        } else {
            // If not an email, treat as employee code - Add prefix for searching with repository
            // employee should only use the numbers in their employeeCode
            Employee employee = employeeRepository.findByEmployeeCode("EMP-" + usernameOrEmployeeCode)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with employee code " + "EMP-" + usernameOrEmployeeCode));
            loginIdentifier = employee.getEmployeeCode();
            user = employee.getUser();
        }

        return CustomUserPrincipal.create(user, loginIdentifier);
    }

    /**
     * Determines if the input is an email address.
     *
     * @param input the input string to check
     * @return true if the input contains '@', false otherwise
     */
    private boolean isEmail(String input) {
        return input.contains("@");
    }
}
