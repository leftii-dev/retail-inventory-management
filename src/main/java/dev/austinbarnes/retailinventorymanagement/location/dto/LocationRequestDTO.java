package dev.austinbarnes.retailinventorymanagement.location.dto;

import java.util.UUID;

/**
 * LocationRequestDTO is a data transfer object that represents the request for a location.
 * It includes fields for the name and location type ID.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param name          the name of the location
 * @param locationTypeID the unique identifier of the location type
 */
public record LocationRequestDTO(
    String name,
    UUID locationTypeID
) {
}
