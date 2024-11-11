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
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> userLogin(@Valid @RequestBody UserLoginRequestDto loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/employee")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> employeeLogin(@Valid @RequestBody EmployeeLoginRequestDto loginRequest) {
        return authService.authenticateEmployee(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> register(@Valid @RequestBody RegistrationRequestDto registrationRequest) {
        return authService.register(registrationRequest);
    }
//
//    @GetMapping("/activate/{token}")
//    public ResponseEntity<?> activateAccount(@PathVariable String token) {
//        return authService.activateAccount(token)
//                .map(user -> ResponseEntity.ok(new ApiResponse(true, "Account activated successfully")))
//                .orElse(ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid or expired activation token")));
//    }

    // Invalidates session
    @PostMapping("/logout")
    public <T> ResponseEntity<ApiResponseDto<T>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ApiResponseDto.noContent();
    }


    // Forward user to Google OAuth2 flow
    @GetMapping("/google")
    public ResponseEntity<Void> oAuth2Google(){
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/oauth/authorization/google"))
                .build();
    }

    // Forward user to GitHub OAuth2 flow
    @GetMapping("/github")
    public ResponseEntity<Void> oAuth2Github(){
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/oauth2/authorization/github"))
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDto<Principal>> getMe(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        return ApiResponseDto.ok(principal);
    }
}
