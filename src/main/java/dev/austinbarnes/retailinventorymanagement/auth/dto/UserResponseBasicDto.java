package dev.austinbarnes.retailinventorymanagement.auth.dto;

import java.util.UUID;

public record UserResponseBasicDto(
        UUID id,
        String name
) implements UserResponseDto{
}
