package dev.austinbarnes.retailinventorymanagement.location.dto;

import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * LocationResponseDetailDTO is a data transfer object that represents the detailed response for a location.
 * It includes fields for the unique identifier, name, creation and modification timestamps,
 * location type, creator and modifier information, and active status of the location.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id             the unique identifier of the location
 * @param name           the name of the location
 * @param createdAt      the timestamp when the location was created
 * @param modifiedAt     the timestamp when the location was last modified
 * @param locationType   the location type of the location
 * @param createdByID    the unique identifier of the user who created the location
 * @param createdByName  the name of the user who created the location
 * @param modifiedByID   the unique identifier of the user who last modified the location
 * @param modifiedByName the name of the user who last modified the location
 * @param active         indicates whether the location is active or not
 */
public record LocationResponseDetailDTO(
    UUID id,
    String name,
    Instant createdAt,
    Instant modifiedAt,
    LocationTypeResponseDTO locationType,
    UUID createdByID,
    String createdByName,
    UUID modifiedByID,
    String modifiedByName,
    boolean active
) implements LocationResponseDTO{
}
