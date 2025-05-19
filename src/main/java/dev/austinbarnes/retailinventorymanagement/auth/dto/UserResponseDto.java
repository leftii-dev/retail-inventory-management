package dev.austinbarnes.retailinventorymanagement.auth.dto;

import java.util.UUID;

/**
 * UserResponseBasicDto represents a basic data transfer object for user responses.
 * It requires methods for user's ID and name.
 */
public interface UserResponseDto {
    UUID id();
    String name();
}
