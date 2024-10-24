package dev.austinbarnes.retailinventorymanagement.location.dto.retail;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record RetailLocationResponseDetailDTO(
        UUID id,
        String retailLocationCode,
        LocationResponseDTO location,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements RetailLocationResponseDTO{
}
