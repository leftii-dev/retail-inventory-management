package dev.austinbarnes.retailinventorymanagement.auth.dto;

import java.util.UUID;

/**
 * UserResponseBasicDto represents a basic data transfer object for user responses.
 * It contains the user's ID and name.
 *
 * @param id   The unique identifier of the user.
 * @param name The name of the user.
 */
public record UserResponseBasicDto(
        UUID id,
        String name
) implements UserResponseDto{
}
