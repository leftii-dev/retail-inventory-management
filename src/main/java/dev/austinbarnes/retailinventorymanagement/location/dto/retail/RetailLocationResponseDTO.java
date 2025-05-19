package dev.austinbarnes.retailinventorymanagement.location.dto.retail;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

/**
 * RetailLocationResponseBasicDTO is a data transfer object that represents the basic response for a retail location.
 * It includes fields for the unique identifier and location details.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id       the unique identifier of the retail location
 * @param location the location details
 */
public interface RetailLocationResponseDTO {
    UUID id();
    LocationResponseDTO location();
}
