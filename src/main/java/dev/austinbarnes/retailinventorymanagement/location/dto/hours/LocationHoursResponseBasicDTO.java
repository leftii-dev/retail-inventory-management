package dev.austinbarnes.retailinventorymanagement.location.dto.hours;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.LocalTime;
import java.util.UUID;

/**
 * LocationHoursResponseBasicDTO is a data transfer object that represents the basic response for location hours.
 * It includes fields for the day of the week, open time, close time, and location details.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id         the unique identifier of the location hours
 * @param dayOfWeek  the day of the week (0-6)
 * @param openTime   the opening time of the location
 * @param closeTime  the closing time of the location
 * @param location   the location details
 */
public record LocationHoursResponseBasicDTO(
        UUID id,
        short dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        LocationResponseDTO location
) implements LocationHoursResponseDTO{
}
