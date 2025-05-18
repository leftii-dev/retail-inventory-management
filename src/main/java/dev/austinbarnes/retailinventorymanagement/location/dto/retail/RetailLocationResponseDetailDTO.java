package dev.austinbarnes.retailinventorymanagement.location.dto.retail;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * RetailLocationResponseDetailDTO is a data transfer object that represents the detailed response for a retail location.
 * It includes fields for the unique identifier, retail location code, location details, timestamps, and user identifiers.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id               the unique identifier of the retail location
 * @param retailLocationCode the code of the retail location
 * @param location         the location details
 * @param createdAt        the timestamp when the retail location was created
 * @param modifiedAt       the timestamp when the retail location was last modified
 * @param createdBy        the unique identifier of the user who created the retail location
 * @param modifiedBy       the unique identifier of the user who last modified the retail location
 * @param active           indicates whether the retail location is active or not
 */
public record RetailLocationResponseDetailDTO(
        UUID id,
        String retailLocationCode,
        LocationResponseDTO location,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements RetailLocationResponseDTO{
}
