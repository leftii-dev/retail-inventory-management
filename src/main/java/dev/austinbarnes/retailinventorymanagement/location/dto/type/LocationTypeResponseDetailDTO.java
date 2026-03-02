package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import java.time.Instant;
import java.util.UUID;

/**
 * LocationTypeResponseDetailDTO is a data transfer object that represents the detailed response for a location type.
 * It includes fields for the unique identifier, name, creation and modification timestamps, creator and modifier IDs, and active status.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id         the unique identifier of the location type
 * @param name       the name of the location type
 * @param createdAt  the timestamp when the location type was created
 * @param modifiedAt the timestamp when the location type was last modified
 * @param createdBy  the unique identifier of the user who created the location type
 * @param modifiedBy the unique identifier of the user who last modified the location type
 * @param active     indicates whether the location type is active or not
 */
public record   LocationTypeResponseDetailDTO(
        UUID id,
        String name,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements LocationTypeResponseDTO{
}
