package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.util.UUID;

/**
 * EmployeePermissionResponseBasicDTO is a Data Transfer Object (DTO) used for representing
 * employee permissions.
 * <p>
 * It contains the IDs of the employee and the permission.
 *
 * @param id        The ID of the employee permission.
 * @param employee  The employee associated with the permission.
 * @param permission The permission associated with the employee.
 */
public record EmployeePermissionResponseBasicDTO(
        UUID id,
        EmployeeResponseDTO employee,
        PermissionResponseDTO permission
) implements EmployeePermissionResponseDTO {
}
