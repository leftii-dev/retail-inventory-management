package dev.austinbarnes.retailinventorymanagement.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * RegistrationRequestDto represents the data transfer object for user registration requests.
 * It contains the email, name, password, and roles fields with validation constraints.
 */
public record RegistrationRequestDto(
        @NotNull
        @Email(message = "Invalid email format")
        String email,
        @NotNull
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,
        @NotNull
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        String password,
        Set<String> roles
) {
}
