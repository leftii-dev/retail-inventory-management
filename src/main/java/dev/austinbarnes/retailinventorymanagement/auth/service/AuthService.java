package dev.austinbarnes.retailinventorymanagement.auth.service;

import dev.austinbarnes.retailinventorymanagement.auth.dto.*;
import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.mapper.UserMapper;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
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
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public ResponseEntity<ApiResponseDto<UserResponseDto>> authenticateUser(UserLoginRequestDto loginRequestDto) {
        UserResponseBasicDto user = userRepository.findByEmail(loginRequestDto.email())
                .map(userMapper::toBasicDto)
                .orElseThrow(() -> new UsernameNotFoundException(loginRequestDto.email()));

        // Authenticate user
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password())
        );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        RequestAttributes attrs = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        return ApiResponseDto.ok(user);
    }

//    @Transactional
//    public ResponseEntity<ApiResponseDto<UserResponseDto>> authenticateEmployee(EmployeeLoginRequestDto loginRequestDto) {
//        Employee employee = employeeRepository.findByEmployeeCode(loginRequestDto.employeeCode())
//                .orElseThrow(() -> new UsernameNotFoundException("Invalid employee code"));
//
//        return Optional.ofNullable(employee.getUser())
//                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
//                .map(user -> ApiResponseDto.<UserResponseDto, UserResponseBasicDto>ok(userMapper.toBasicDto(user)))
//                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
//    }

    @Transactional
    public ResponseEntity<ApiResponseDto<UserResponseDto>> authenticateOAuth2User(OAuth2User oAuth2User, String provider){
        String email = oAuth2User.getAttribute("email");
        String providerId = oAuth2User.getAttribute("sub");

        User user = userRepository.findByEmail(email)
                .map(existingUser -> updateOAuth2User(existingUser, provider, providerId))
                .orElseGet(() -> createOAuth2User(oAuth2User, provider, providerId));

        return generateAuthResponse(user);
    }

    private User updateOAuth2User(User existingUser, String provider, String providerId) {
        if(existingUser.getOauthProvider() != null &&
                !existingUser.getOauthProvider().equals(provider)) {
            // TODO custom exception for existing oauth provider
            throw new RuntimeException("User account already associated with provider. Try logging in using " + existingUser.getOauthProvider());
        }

        existingUser.setOauthProvider(provider);
        existingUser.setOauthProviderId(providerId);
        return userRepository.save(existingUser);
    }

    private User createOAuth2User(OAuth2User oAuth2User, String provider, String providerId) {
        Role userRole = roleRepository.findByName("SHOPPER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User newUser = new User();
        newUser.setEmail(oAuth2User.getAttribute("email"));
        newUser.setName(oAuth2User.getAttribute("name"));
        newUser.setOauthProvider(provider);
        newUser.setOauthProviderId(providerId);
        newUser.setPictureUrl(oAuth2User.getAttribute("picture"));
        newUser.setRoles(Set.of(userRole));
        newUser.setEnabled(true);
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialsNonExpired(true);

        return userRepository.save(newUser);
    }

    private ResponseEntity<ApiResponseDto<UserResponseDto>> generateAuthResponse(User user) {
        AuthResponseDto response = new AuthResponseDto("Authentication successful", userMapper.toBasicDto(user));
        return ApiResponseDto.ok(userMapper.toBasicDto(user));
    }

    private boolean isEmployeeUser(User user) {
        return user.getEmployee() != null;
    }
}
