package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.util.UUID;

public record EmployeePermissionResponseBasicDTO(
        UUID id,
        EmployeeResponseDTO employee,
        PermissionResponseDTO permission
) implements EmployeePermissionResponseDTO {
}
