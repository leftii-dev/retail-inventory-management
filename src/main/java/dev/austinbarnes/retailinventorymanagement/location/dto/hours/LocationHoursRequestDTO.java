package dev.austinbarnes.retailinventorymanagement.location.dto.hours;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalTime;
import java.util.UUID;

/**
 * LocationHoursRequestDTO is a data transfer object that represents the request for location hours.
 * It includes fields for the day of the week, open time, close time, and location ID.
 * The day of the week is validated to be between 0 and 6 (inclusive).
 */
public record LocationHoursRequestDTO (
        @Min(value = 0, message = "Day of week value cannot be negative (0-6)") @Max(value = 7, message = "Day of week value cannot be over 6 (0-6)") short dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        UUID locationID
){
}
