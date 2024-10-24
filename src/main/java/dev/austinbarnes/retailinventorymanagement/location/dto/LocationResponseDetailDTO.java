package dev.austinbarnes.retailinventorymanagement.location.dto;

import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record LocationResponseDetailDTO(
    UUID id,
    String name,
    Instant createdAt,
    Instant modifiedAt,
    LocationTypeResponseDTO locationType,
    UUID createdByID,
    String createdByName,
    UUID modifiedByID,
    String modifiedByName,
    boolean deleted
) implements LocationResponseDTO{
}
