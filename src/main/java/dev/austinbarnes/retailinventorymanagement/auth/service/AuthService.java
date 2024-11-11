package dev.austinbarnes.retailinventorymanagement.auth.service;

import dev.austinbarnes.retailinventorymanagement.auth.dto.*;
import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.mapper.UserMapper;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
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
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public ResponseEntity<ApiResponseDto<UserResponseDto>> authenticateUser(UserLoginRequestDto loginRequestDto) {
        UserResponseBasicDto userResponse = userRepository.findByEmail(loginRequestDto.email())
                .map(userMapper::toBasicDto)
                .orElseThrow(() -> new UsernameNotFoundException(loginRequestDto.email()));

        authenticate(loginRequestDto.email(), loginRequestDto.password());

        return ApiResponseDto.ok(userResponse);
    }

    public ResponseEntity<ApiResponseDto<UserResponseDto>> authenticateEmployee(EmployeeLoginRequestDto loginRequestDto) {
        Employee employee = employeeRepository.findByEmployeeCode("EMP-" + loginRequestDto.employeeCode())
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found"));

        User user = userRepository.findById(employee.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found"));

        UserResponseBasicDto userResponse = userMapper.toBasicDto(user);

        authenticate(user.getEmail(), loginRequestDto.password());

        return ApiResponseDto.ok(userResponse);
    }

    public ResponseEntity<ApiResponseDto<UserResponseDto>> register(RegistrationRequestDto registrationRequest){
        return ApiResponseDto.ok(userMapper.toBasicDto(userRepository.save(userMapper.toEntity(registrationRequest)))
        );
    }

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
    }

    private boolean isEmployeeUser(User user) {
        return user.getEmployee() != null;
    }
}
