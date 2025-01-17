package dev.austinbarnes.retailinventorymanagement.auth.dto;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;

import java.util.Set;
import java.util.UUID;

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
