package dev.austinbarnes.retailinventorymanagement.location.dto;

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
