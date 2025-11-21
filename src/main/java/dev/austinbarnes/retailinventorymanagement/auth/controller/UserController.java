package dev.austinbarnes.retailinventorymanagement.auth.controller;

import dev.austinbarnes.retailinventorymanagement.auth.dto.UserRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserResponseDto;
import dev.austinbarnes.retailinventorymanagement.auth.service.UserService;
import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Retrieve all users
     *
     * @return List of UserResponseDto
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<UserResponseDto>>> getAllUsers(){
        return userService.getAllUsers();
    }

    /**
     * Retrieve a user by their ID
     *
     * @param id UUID of the user
     * @return UserResponseDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> getUserByID(@PathVariable UUID id) {
        return userService.getUserByID(id);
    }

    /**
     * Retrieve the currently logged-in user
     *
     * @param session HttpSession containing user information
     * @return UserResponseDto of the current user
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> getCurrentUser(HttpSession session) {
        String id = (String) session.getAttribute("userId");
        return userService.getUserByID(UUID.fromString(id));
    }

    /**
     * Update a user's information
     *
     * @param id UUID of the user to update
     * @param request UserRequestDto containing updated user information
     * @return Updated UserResponseDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequestDto request) {
        return userService.updateUser(id, request);
    }

}
