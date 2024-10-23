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
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements EmployeePermissionResponseDTO {
}
