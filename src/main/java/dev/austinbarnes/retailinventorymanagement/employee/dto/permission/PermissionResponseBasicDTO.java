package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import java.util.UUID;

/**
 * EmployeePermissionRequestDTO is a Data Transfer Object (DTO) used for creating or updating
 * employee permissions.
 * <p>
 * It contains the IDs of the employee and the role.
 */
public record PermissionResponseBasicDTO(
    UUID id,
    String name,
    String description
) implements PermissionResponseDTO {
}
