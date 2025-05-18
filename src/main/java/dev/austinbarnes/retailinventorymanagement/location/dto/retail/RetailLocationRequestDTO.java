package dev.austinbarnes.retailinventorymanagement.location.dto.retail;

import java.util.UUID;

/**
 * RetailLocationRequestDTO is a data transfer object that represents the request for a retail location.
 * It includes a field for the location ID.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param locationID the unique identifier of the retail location
 */
public record RetailLocationRequestDTO(
        UUID locationID
) {
}
