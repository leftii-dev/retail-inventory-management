package dev.austinbarnes.retailinventorymanagement.location.dto.warehouse;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

public record WarehouseLocationResponseDetailDTO(
        UUID id,
        String warehouseCode,
        LocationResponseDTO location,
        boolean deleted
) implements WarehouseLocationResponseDTO {
}
