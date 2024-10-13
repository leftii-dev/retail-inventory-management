package dev.austinbarnes.retailinventorymanagement.location.dto.warehouse;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

public record WarehouseLocationResponseAdminDTO(
        UUID id,
        String warehouseCode,
        LocationResponseDTO location,
        boolean deleted
) implements WarehouseLocationResponseDTO {
}
