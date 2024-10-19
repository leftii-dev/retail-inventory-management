package dev.austinbarnes.retailinventorymanagement.employee.dto.role;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record EmployeeRoleResponseDetailDTO(
        UUID id,
        EmployeeResponseDTO employee,
        RoleResponseDTO role,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements EmployeeRoleResponseDTO {
}
