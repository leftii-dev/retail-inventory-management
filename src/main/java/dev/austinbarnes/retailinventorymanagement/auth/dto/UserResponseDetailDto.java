package dev.austinbarnes.retailinventorymanagement.auth.dto;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;

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
        UUID id,
        String email,
        String name,
        String oauthProvider,
        UUID employeeId,
        Set<Role> roles,
        boolean enabled,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean active
) implements UserResponseDto{
}
