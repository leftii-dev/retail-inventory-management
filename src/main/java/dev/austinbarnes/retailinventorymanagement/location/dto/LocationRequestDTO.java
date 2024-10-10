package dev.austinbarnes.retailinventorymanagement.location.dto;

import java.util.UUID;

public record LocationRequestDTO(
    String name,
    UUID locationTypeID
) {
}
