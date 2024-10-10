package dev.austinbarnes.retailinventorymanagement.location.dto.warehouse;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

public record WarehouseLocationResponseClientDTO(
        UUID id,
        LocationResponseDTO location
) implements WarehouseLocationResponseDTO {
}
