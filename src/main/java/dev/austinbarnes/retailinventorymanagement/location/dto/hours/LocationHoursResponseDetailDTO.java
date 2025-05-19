package dev.austinbarnes.retailinventorymanagement.location.dto.hours;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

/**
 * LocationHoursResponseDetailDTO is a data transfer object that represents the detailed response for location hours.
 * It includes fields for the day of the week, open time, close time, location details, and timestamps for creation and modification.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id         the unique identifier of the location hours
 * @param dayOfWeek  the day of the week (0-6)
 * @param openTime   the opening time of the location
 * @param closeTime  the closing time of the location
 * @param location   the location details
 * @param createdAt  the timestamp when the record was created
 * @param modifiedAt the timestamp when the record was last modified
 * @param createdBy  the ID of the user who created the record
 * @param modifiedBy the ID of the user who last modified the record
 * @param active     indicates whether the record is active or not
 */
public record LocationHoursResponseDetailDTO(
        UUID id,
        short dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        LocationResponseDTO location,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements LocationHoursResponseDTO{
}
