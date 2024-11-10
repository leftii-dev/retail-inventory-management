package dev.austinbarnes.retailinventorymanagement.auth.dto;

public record AuthResponseDto(
        String message,
        UserResponseDto userResponseDto
) {
}
