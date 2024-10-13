package dev.austinbarnes.retailinventorymanagement.location.dto.hours;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.LocalTime;
import java.util.UUID;

public record LocationHoursResponseClientDTO(
        UUID id,
        short dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        LocationResponseDTO location
) implements LocationHoursResponseDTO{
}
