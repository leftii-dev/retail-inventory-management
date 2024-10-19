package dev.austinbarnes.retailinventorymanagement.location.dto.hours;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

public record LocationHoursResponseDetailDTO(
        UUID id,
        short dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        LocationResponseDTO location,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements LocationHoursResponseDTO{
}
