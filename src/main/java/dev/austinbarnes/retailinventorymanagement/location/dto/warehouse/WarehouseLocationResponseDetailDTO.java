package dev.austinbarnes.retailinventorymanagement.location.dto.warehouse;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record WarehouseLocationResponseDetailDTO(
        UUID id,
        String warehouseCode,
        LocationResponseDTO location,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements WarehouseLocationResponseDTO {
}
