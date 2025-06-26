package dev.austinbarnes.retailinventorymanagement.auth.dto;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

/**
 * UserResponseDetailDto represents a detailed data transfer object for user responses.
 * It contains the user's ID, email, name, OAuth provider, employee ID, roles, and account status.
 *
 * @param id                   The unique identifier of the user.
 * @param email                The email address of the user.
 * @param name                 The name of the user.
 * @param oauthProvider        The OAuth provider used for authentication.
 * @param employeeId           The unique identifier of the employee associated with the user.
 * @param roles                The set of roles assigned to the user.
 * @param enabled              Indicates if the user's account is enabled.
 * @param accountNonExpired    Indicates if the user's account is non-expired.
 * @param accountNonLocked     Indicates if the user's account is non-locked.
 * @param credentialsNonExpired Indicates if the user's credentials are non-expired.
 * @param active               Indicates if the user is active.
 */
public record UserResponseDetailDto(
        @Schema(
                description = "Unique identifier of the user",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,
        @Schema(
                description = "Email address of the user",
                example = "user@mail.com"
        )
        String email,
        @Schema(
                description = "Name of the user",
                example = "John Doe"
        )
        String name,
        @Schema(
                description = "OAuth provider used for authentication",
                example = "google"
        )
        String oauthProvider,
        @Schema(
                description = "Unique identifier of the employee associated with the user",
                example = "123e4567-e89b-12d3-a456-426614174001"
        )
        UUID employeeId,
        @Schema(
                description = "Set of roles assigned to the user",
                example = "[\"USER\", \"ADMIN\"]"
        )
        Set<Role> roles,
        @Schema(
                description = "Indicates if the user's account is enabled",
                example = "true"
        )
        boolean enabled,
        @Schema(
                description = "Indicates if the user's account is non-expired",
                example = "true"
        )
        boolean accountNonExpired,
        @Schema(
                description = "Indicates if the user's account is non-locked",
                example = "true"
        )
        boolean accountNonLocked,
        @Schema(
                description = "Indicates if the user's credentials are non-expired",
                example = "true"
        )
        boolean credentialsNonExpired,
        @Schema(
                description = "Indicates if the user is active",
                example = "true"
        )
        boolean active
) implements UserResponseDto{
}
