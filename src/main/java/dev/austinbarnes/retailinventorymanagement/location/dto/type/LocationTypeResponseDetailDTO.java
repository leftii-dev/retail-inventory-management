package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record LocationTypeResponseDetailDTO(
        UUID id,
        String name,
        Instant createdAt,
        Instant modifiedAt,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) implements LocationTypeResponseDTO{
}
