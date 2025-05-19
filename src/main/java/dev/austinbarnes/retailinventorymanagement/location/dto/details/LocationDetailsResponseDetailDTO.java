package dev.austinbarnes.retailinventorymanagement.location.dto.details;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * LocationDetailsResponseDetailDTO is a data transfer object that represents the detailed information of a location.
 * It includes fields for address, city, state, zip code, phone, fax, email, notes, location ID,
 * manager ID, created and modified timestamps, and active status.
 * This DTO is used to transfer detailed data between layers of the application.
 *
 * @param id          the unique identifier of the location details
 * @param addressLine1 the first line of the address
 * @param addressLine2 the second line of the address
 * @param city        the city of the location
 * @param state       the state of the location
 * @param zipCode     the zip code of the location
 * @param phone       the phone number of the location
 * @param fax         the fax number of the location
 * @param email       the email address of the location
 * @param notes       additional notes about the location
 * @param location    the location details
 * @param manager     the manager of the location
 */
public record LocationDetailsResponseDetailDTO(
        UUID id,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String zipCode,
        String phone,
        String fax,
        String email,
        String notes,
        LocationResponseDTO location,
        EmployeeResponseDTO manager,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements LocationDetailsResponseDTO{
}
