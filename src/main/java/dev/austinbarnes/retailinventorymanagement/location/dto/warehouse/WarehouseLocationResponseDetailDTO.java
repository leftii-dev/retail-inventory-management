package dev.austinbarnes.retailinventorymanagement.location.dto.warehouse;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * WarehouseLocationResponseDetailDTO is a data transfer object that represents the detailed response for a warehouse location.
 * It includes fields for the unique identifier, warehouse code, location details, timestamps, and user identifiers.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id          the unique identifier of the warehouse location
 * @param warehouseCode the code of the warehouse
 * @param location    the location details
 * @param createdAt   the timestamp when the record was created
 * @param modifiedAt  the timestamp when the record was last modified
 * @param createdBy   the unique identifier of the user who created the record
 * @param modifiedBy  the unique identifier of the user who last modified the record
 * @param active      indicates whether the record is active or not
 */
public record WarehouseLocationResponseDetailDTO(
        UUID id,
        String warehouseCode,
        LocationResponseDTO location,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements WarehouseLocationResponseDTO {
}
