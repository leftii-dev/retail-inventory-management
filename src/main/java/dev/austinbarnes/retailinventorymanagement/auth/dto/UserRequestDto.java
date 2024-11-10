package dev.austinbarnes.retailinventorymanagement.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserRequestDto(
    @Email(message = "Invalid email format") String email,
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") String name,
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters") String password,
    String pictureUrl,
    List<String> roles,
    Boolean enabled,
    Boolean accountNonExpired,
    Boolean accountNonLocked,
    Boolean credentialsNonExpired
) {
}
