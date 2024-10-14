package dev.austinbarnes.retailinventorymanagement.location.dto.warehouse;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

public record WarehouseLocationResponseBasicDTO(
        UUID id,
        LocationResponseDTO location
) implements WarehouseLocationResponseDTO {
}
