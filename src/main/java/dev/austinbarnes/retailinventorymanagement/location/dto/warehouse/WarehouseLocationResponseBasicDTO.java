package dev.austinbarnes.retailinventorymanagement.location.dto.warehouse;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

/**
 * WarehouseLocationResponseBasicDTO is a data transfer object that represents the basic response for a warehouse location.
 * It includes fields for the unique identifier and location details.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id       the unique identifier of the warehouse location
 * @param location the location details
 */
public record WarehouseLocationResponseBasicDTO(
        UUID id,
        String warehouseCode,
        LocationResponseDTO location
) implements WarehouseLocationResponseDTO {
}
