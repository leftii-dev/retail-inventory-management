package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import java.util.UUID;

/**
 * LocationTypeResponseBasicDTO is a data transfer object that represents the basic response for a location type.
 * It includes fields for the unique identifier and name of the location type.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id   the unique identifier of the location type
 * @param name the name of the location type
 */
public record LocationTypeResponseBasicDTO(
        UUID id,
        String name
) implements LocationTypeResponseDTO{
}
