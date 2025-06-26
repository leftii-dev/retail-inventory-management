package dev.austinbarnes.retailinventorymanagement.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * UserRequestDto represents the data transfer object for user requests.
 * It contains fields for email, name, password, picture URL, roles, and account status.
 */
public record UserRequestDto(
    @Email(message = "Invalid email format") String email,
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") String name,
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters") String password,
    @Schema(
        description = "URL of the user's profile picture",
        example = "https://example.com/profile.jpg"
    )
    String pictureUrl,
    @Schema(
        description = "List of roles assigned to the user",
        example = "[\"USER\", \"ADMIN\"]"
    )
    List<String> roles,
    @Schema(
        description = "Indicates whether the user account is enabled",
        example = "true"
    )
    Boolean enabled,
    @Schema(
        description = "Indicates whether the user account is non-expired",
        example = "true"
    )
    Boolean accountNonExpired,
    @Schema(
        description = "Indicates whether the user account is non-locked",
        example = "true"
    )
    Boolean accountNonLocked,
    @Schema(
        description = "Indicates whether the user's credentials are non-expired",
        example = "true"
    )
    Boolean credentialsNonExpired
) {
}
