package dev.austinbarnes.retailinventorymanagement.auth.controller;

import dev.austinbarnes.retailinventorymanagement.auth.dto.*;
import dev.austinbarnes.retailinventorymanagement.auth.service.AuthService;
import dev.austinbarnes.retailinventorymanagement.auth.service.UserService;
import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

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
    private final UserService userService;

    /**
     * Handles user login requests.
     *
     * @param loginRequest the login request containing user credentials
     * @return a response entity containing the authentication result
     */
    @Operation(summary = "User Login", description = "Authenticates a user with their credentials.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User logged in successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {UserResponseBasicDto.class, UserResponseDetailDto.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
    })
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
    @Operation(
            summary = "Employee Login",
            description = "Authenticates an employee with their credentials.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee logged in successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {UserResponseBasicDto.class, UserResponseDetailDto.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
    })
    @PostMapping("/employee")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> employeeLogin(
            @Parameter(description = "Employee Login Request Details") @Valid @RequestBody EmployeeLoginRequestDto loginRequest) {
        return authService.authenticateEmployee(loginRequest);
    }

    /**
     * Handles user registration requests.
     *
     * @param registrationRequest the registration request containing user details
     * @return a response entity containing the registration result
     */
    @Operation(
            summary = "User Registration",
            description = "Registers a new user with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {UserResponseBasicDto.class, UserResponseDetailDto.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
    })

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
    @Operation(
            summary = "Activate Account",
            description = "Activates a user account using the provided activation token."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User account activated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {UserResponseBasicDto.class, UserResponseDetailDto.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
    })
    @PostMapping("/activate/{token}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> activateAccount(@PathVariable String token) {
        return authService.activate(token);
    }

    /**
     * Handles credential to OAuth2 link requests.
     *
     * @param token the activation token
     * @return a response entity containing the linking result
     */
    @Operation(
            summary = "Link Credential Account to OAuth2 Account",
            description = "Links a user account to an existing OAuth2 account using the provided link token."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User account linked successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {UserResponseBasicDto.class, UserResponseDetailDto.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
    })
    @PostMapping("/link/{token}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> link(@PathVariable String token) {
        return authService.linkCredentialsToOAuth(token);
    }

    /**
     * Handles logout requests.
     *
     * @param request the HTTP request
     * @return a response entity indicating the logout result
     */
    @Operation(
            summary = "Logout",
            description = "Logs out the user by invalidating the session."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User logged out successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {UserResponseBasicDto.class, UserResponseDetailDto.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
    })
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
    @Operation(
            summary = "Google OAuth2 Login",
            description = "Forwards the request to Google OAuth2 authorization endpoint."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    description = "Google OAuth2 authorization endpoint redirect",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {UserResponseBasicDto.class, UserResponseDetailDto.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
    })
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
    @Operation(
            summary = "GitHub OAuth2 Login",
            description = "Forwards the request to GitHub OAuth2 authorization endpoint.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Github OAuth2 authorization endpoint redirect",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {UserResponseBasicDto.class, UserResponseDetailDto.class}
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
    })
    @GetMapping("/github")
    public ResponseEntity<Void> oAuth2Github(){
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/oauth2/authorization/github"))
                .build();
    }

    @GetMapping("/self")
    public ResponseEntity<UserDetails> getCurrentUserPrincipal(@AuthenticationPrincipal UserDetails userDetails) {
            return ResponseEntity.ok(userDetails);
    }

    @PostMapping("/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/session-status")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> getSessionStatus(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return userService.getUserByID(UUID.fromString(session.getAttribute("userId").toString()));
    }

    @GetMapping("/refresh-session")
    public ResponseEntity<?> refreshSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired");
        }


        // Set the updated cookie to front end
        Cookie cookie = new Cookie("SESSION", session.getId());
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // TODO: Change in prod
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
