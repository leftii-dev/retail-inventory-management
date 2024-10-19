package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record LocationTypeResponseDetailDTO(
        UUID id,
        String name,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements LocationTypeResponseDTO{
}