package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import java.util.UUID;

/**
 * EmployeePermissionRequestDTO is a Data Transfer Object (DTO) used for creating or updating
 * employee permissions.
 * <p>
 * It contains the IDs of the employee and the role.
 *
 * @param employeeID The ID of the employee.
 * @param roleID     The ID of the role.
 */
public record EmployeePermissionRequestDTO(
        UUID employeeID,
        UUID roleID
) {
}
