package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.util.UUID;

/**
 * EmployeePermissionResponseBasicDTO is a Data Transfer Object (DTO) used for representing
 * employee permissions.
 * <p>
 * It contains the IDs of the employee and the permission.
 */
public interface EmployeePermissionResponseDTO {
    UUID id();
    EmployeeResponseDTO employee();
    PermissionResponseDTO permission();
}
