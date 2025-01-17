package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record EmployeePermissionResponseDetailDTO(
        UUID id,
        EmployeeResponseDTO employee,
        PermissionResponseDTO permission,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean deleted
) implements EmployeePermissionResponseDTO {
}
