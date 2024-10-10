package dev.austinbarnes.retailinventorymanagement.location.dto;

import java.time.LocalTime;
import java.util.UUID;

public record LocationHoursResponseAdminDTO(
        UUID id,
        short dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        LocationResponseDTO location,
        boolean deleted
) implements LocationHoursResponseDTO{
}
