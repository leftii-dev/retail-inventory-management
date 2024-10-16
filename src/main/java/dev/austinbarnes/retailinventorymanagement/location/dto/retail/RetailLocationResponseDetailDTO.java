package dev.austinbarnes.retailinventorymanagement.location.dto.retail;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record RetailLocationResponseDetailDTO(
        UUID id,
        String retailLocationCode,
        LocationResponseDTO location,
        Instant createdAt,
        Instant modifiedAt,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) implements RetailLocationResponseDTO{
}
