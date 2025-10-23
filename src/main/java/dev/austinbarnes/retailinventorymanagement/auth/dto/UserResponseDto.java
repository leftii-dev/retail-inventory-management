package dev.austinbarnes.retailinventorymanagement.auth.dto;

import java.util.Set;
import java.util.UUID;

/**
 * UserResponseBasicDto represents a basic data transfer object for user responses.
 * It requires methods for user's ID and name, email, and pictureUrl.
 */
public interface UserResponseDto {
    UUID id();
    String name();
    String email();
    String pictureUrl();
    Set<String> roles();
}
