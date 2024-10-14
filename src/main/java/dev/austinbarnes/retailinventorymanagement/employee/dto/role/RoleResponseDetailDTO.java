package dev.austinbarnes.retailinventorymanagement.employee.dto.role;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record RoleResponseDetailDTO(
        UUID id,
        String name,
        String description,
        Instant createdAt,
        Instant modifiedAt,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) implements RoleResponseDTO {
}