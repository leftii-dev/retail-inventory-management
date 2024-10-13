package dev.austinbarnes.retailinventorymanagement.location.dto.warehouse;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

public interface WarehouseLocationResponseDTO {
    UUID id();
    LocationResponseDTO location();
}
