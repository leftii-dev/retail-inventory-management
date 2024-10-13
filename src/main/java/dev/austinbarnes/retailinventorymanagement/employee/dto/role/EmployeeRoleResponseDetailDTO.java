package dev.austinbarnes.retailinventorymanagement.employee.dto.role;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.util.UUID;

public record EmployeeRoleResponseDetailDTO(
        UUID id,
        EmployeeResponseDTO employee,
        RoleResponseDTO role,
        boolean deleted
) implements EmployeeRoleResponseDTO {
}
