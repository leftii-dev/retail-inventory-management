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
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) implements EmployeeRoleResponseDTO {
}
