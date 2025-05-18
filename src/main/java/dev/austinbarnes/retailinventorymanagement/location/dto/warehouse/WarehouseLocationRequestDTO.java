package dev.austinbarnes.retailinventorymanagement.location.dto.warehouse;

import java.util.UUID;

/**
 * WarehouseLocationRequestDTO is a data transfer object that represents the request for a warehouse location.
 * It includes a field for the location ID.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param locationID the unique identifier of the warehouse location
 */
public record WarehouseLocationRequestDTO(
        UUID locationID
) {
}
