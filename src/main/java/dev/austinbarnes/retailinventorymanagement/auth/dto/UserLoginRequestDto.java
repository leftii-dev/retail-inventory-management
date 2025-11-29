package dev.austinbarnes.retailinventorymanagement.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * UserLoginRequestDto represents the data transfer object for user login requests.
 * It contains the email and password fields with validation constraints.
 */
public record UserLoginRequestDto(
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        String password
) {
}
