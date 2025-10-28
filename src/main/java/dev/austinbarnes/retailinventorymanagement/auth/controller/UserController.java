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

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<UserResponseDto>>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> getCurrentUser(HttpSession session) {
        String id = (String) session.getAttribute("userId");
        return userService.getUserByID(UUID.fromString(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequestDto request) {
        return userService.updateUser(id, request);
    }

}
