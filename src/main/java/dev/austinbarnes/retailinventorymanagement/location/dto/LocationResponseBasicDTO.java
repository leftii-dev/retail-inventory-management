package dev.austinbarnes.retailinventorymanagement.location.dto;

import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDTO;

import java.util.UUID;

/**
 * LocationResponseBasicDTO is a data transfer object that represents the basic response for a location.
 * It includes fields for the unique identifier, name, and location type of the location.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id             the unique identifier of the location
 * @param name           the name of the location
 * @param locationType   the location type of the location
 */
public record LocationResponseBasicDTO(
        UUID id,
        String name,
        LocationTypeResponseDTO locationType
) implements LocationResponseDTO{
}
