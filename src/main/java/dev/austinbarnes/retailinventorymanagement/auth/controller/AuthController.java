package dev.austinbarnes.retailinventorymanagement.auth.controller;

import dev.austinbarnes.retailinventorymanagement.auth.dto.EmployeeLoginRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.RegistrationRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserLoginRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserResponseDto;
import dev.austinbarnes.retailinventorymanagement.auth.service.AuthService;
import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

/**
 * AuthController handles authentication and registration requests.
 * It provides endpoints for user login, employee login, registration,
 * account activation, logout, and OAuth2 login with Google and GitHub.
 */
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Handles user login requests.
     *
     * @param loginRequest the login request containing user credentials
     * @return a response entity containing the authentication result
     */
    @PostMapping("/user")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> userLogin(@Valid @RequestBody UserLoginRequestDto loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    /**
     * Handles employee login requests.
     *
     * @param loginRequest the login request containing employee credentials
     * @return a response entity containing the authentication result
     */
    @PostMapping("/employee")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> employeeLogin(@Valid @RequestBody EmployeeLoginRequestDto loginRequest) {
        return authService.authenticateEmployee(loginRequest);
    }

    /**
     * Handles user registration requests.
     *
     * @param registrationRequest the registration request containing user details
     * @return a response entity containing the registration result
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> register(@Valid @RequestBody RegistrationRequestDto registrationRequest) {
        return authService.register(registrationRequest);
    }

    /**
     * Handles account activation requests.
     *
     * @param token the activation token
     * @return a response entity containing the activation result
     */
    @PostMapping("/activate/{token}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> activateAccount(@PathVariable String token) {
        return authService.activate(token);
    }

    /**
     * Handles logout requests.
     *
     * @param request the HTTP request
     * @return a response entity indicating the logout result
     */
    @PostMapping("/logout")
    public <T> ResponseEntity<ApiResponseDto<T>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ApiResponseDto.noContent();
    }


    /**
     * Forwards requests for Google OAuth2.
     *
     * @return a response entity indicating successful forward
     */
    @GetMapping("/google")
    public ResponseEntity<Void> oAuth2Google(){
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/oauth/authorization/google"))
                .build();
    }

    /**
     * Forwards requests for GitHub OAuth2.
     *
     * @return a response entity indicating successful forward
     */
    @GetMapping("/github")
    public ResponseEntity<Void> oAuth2Github(){
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/oauth2/authorization/github"))
                .build();
    }

    /**
     * Handles OAuth2 login requests.
     *
     * @param request the HTTP request
     * @return a response entity with current user principal
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponseDto<Principal>> getMe(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        return ApiResponseDto.ok(principal);
    }
}
