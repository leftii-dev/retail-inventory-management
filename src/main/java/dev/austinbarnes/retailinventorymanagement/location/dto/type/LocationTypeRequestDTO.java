package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import jakarta.validation.constraints.Size;

/**
 * LocationTypeRequestDTO is a data transfer object that represents the request for a location type.
 * It includes a field for the name of the location type.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param name the name of the location type
 */
public record LocationTypeRequestDTO(
        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters") String name
) {
}
