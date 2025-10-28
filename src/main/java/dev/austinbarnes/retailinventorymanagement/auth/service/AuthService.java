package dev.austinbarnes.retailinventorymanagement.auth.service;

import dev.austinbarnes.retailinventorymanagement.auth.CustomUserPrincipal;
import dev.austinbarnes.retailinventorymanagement.auth.dto.*;
import dev.austinbarnes.retailinventorymanagement.auth.entity.ActivationToken;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.mapper.UserMapper;
import dev.austinbarnes.retailinventorymanagement.auth.repo.ActivationTokenRepository;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import dev.austinbarnes.retailinventorymanagement.exception.ActivationTokenNotFoundException;
import dev.austinbarnes.retailinventorymanagement.exception.DuplicateEmailRegistrationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

/**
 * AuthService handles authentication and registration logic for users and employees.
 * It provides methods for user login, employee login, registration, and account activation.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final ActivationTokenRepository activationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivationTokenService activationTokenService;


    /**
     * Authenticates a user with the provided login credentials.
     *
     * @param loginRequestDto the login request containing user credentials
     * @return a response entity containing the authentication result
     */
    @Transactional
    public ResponseEntity<ApiResponseDto<UserResponseDto>> authenticateUser(UserLoginRequestDto loginRequestDto) {
        UserResponseBasicDto userResponse = userRepository.findByEmail(loginRequestDto.email())
                .map(user -> userMapper.toBasicDto(user, roleRepository))
                .orElseThrow(() -> new UsernameNotFoundException(loginRequestDto.email()));

        authenticate(loginRequestDto.email(), loginRequestDto.password());

        return ApiResponseDto.ok(userResponse);
    }

    /**
     * Authenticates an employee with the provided login credentials.
     *
     * @param loginRequestDto the login request containing employee credentials
     * @return a response entity containing the authentication result
     */
    public ResponseEntity<ApiResponseDto<UserResponseDto>> authenticateEmployee(EmployeeLoginRequestDto loginRequestDto) {
        Employee employee = employeeRepository.findByEmployeeCode("EMP-" + loginRequestDto.employeeCode())
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found"));

        User user = userRepository.findById(employee.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found"));

        UserResponseBasicDto userResponse = userMapper.toBasicDto(user, roleRepository);

        authenticate(user.getEmail(), loginRequestDto.password());

        return ApiResponseDto.ok(userResponse);
    }

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registrationRequest the registration request containing user details
     * @return a response entity containing the registration result
     */
    public ResponseEntity<ApiResponseDto<UserResponseDto>> register(RegistrationRequestDto registrationRequest){
        User existing = userRepository.findByEmail(registrationRequest.email()).orElse(null);
            if(existing != null) {
                if (existing.getPassword() != null) {
                    throw new DuplicateEmailRegistrationException(registrationRequest.email(), "Email already registered.");
                }
                // Account already exists as OAuth - Set password, enable credential account
                userMapper.updateEntityFromRegistrationRequestDto(registrationRequest, existing);
                existing.setPassword(passwordEncoder.encode(registrationRequest.password()));
                existing.setEnabled(true);
                return ApiResponseDto.ok(userMapper.toBasicDto(userRepository.save(existing), roleRepository));
            }

        User user = userRepository.save(userMapper.toEntity(registrationRequest, roleRepository, passwordEncoder));


        activationTokenService.activateAndSendEmail(user.getId(), registrationRequest.email());
        return ApiResponseDto.created(userMapper.toBasicDto(user, roleRepository));
    }

    /**
     * Activates a user account using the provided activation token.
     *
     * @param token the activation token
     * @return a response entity containing the activation result
     */
    @Transactional
    public ResponseEntity<ApiResponseDto<UserResponseDto>> activate(String token) {
        ActivationToken activationToken = activationTokenRepository.findById(UUID.fromString(token))
                .orElseThrow(() -> new ActivationTokenNotFoundException(token));

        User user = userRepository.findById(activationToken.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User attached to token not found. Try again"));

        user.setEnabled(true);
        userRepository.save(user);

        return ApiResponseDto.ok(userMapper.toBasicDto(user, roleRepository));
    }

    /**
     * Authenticates a user with the provided email and password.
     *
     * @param email the user's email
     * @param password the user's password
     */
    private void authenticate(String email, String password) {
        // Authenticate user
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        RequestAttributes attrs = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        // Adding user info to the session for easy access
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();
        session.setAttribute("userId", principal.getId().toString());
        session.setAttribute("email", principal.getEmail());
        session.setAttribute("name", principal.getName());
        session.setAttribute("isAuthenticated", true);
    }

}
