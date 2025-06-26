package dev.austinbarnes.retailinventorymanagement.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * UserResponseBasicDto represents a basic data transfer object for user responses.
 * It contains the user's ID and name.
 *
 * @param id   The unique identifier of the user.
 * @param name The name of the user.
 */
public record UserResponseBasicDto(
        @Schema(
                description = "Unique identifier of the user",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,
        @Schema(
                description = "Name of the user",
                example = "John Doe"
        )
        String name
) implements UserResponseDto{
}
