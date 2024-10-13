package dev.austinbarnes.retailinventorymanagement.employee.dto.role;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.util.UUID;

public record EmployeeRoleResponseBasicDTO(
        UUID id,
        EmployeeResponseDTO employee,
        RoleResponseDTO role
) implements EmployeeRoleResponseDTO {
}
