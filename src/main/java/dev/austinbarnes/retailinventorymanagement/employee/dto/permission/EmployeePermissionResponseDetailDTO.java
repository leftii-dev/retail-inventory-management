package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * EmployeePermissionResponseDetailDTO is a Data Transfer Object (DTO) used for representing
 * employee permissions in detail.
 * <p>
 * It contains the IDs of the employee and the permission, along with timestamps and user IDs for
 * creation and modification.
 *
 * @param id        The ID of the employee permission.
 * @param employee  The employee associated with the permission.
 * @param permission The permission associated with the employee.
 * @param createdAt  The timestamp when the permission was created.
 * @param modifiedAt The timestamp when the permission was last modified.
 * @param createdBy  The ID of the user who created the permission.
 * @param modifiedBy The ID of the user who last modified the permission.
 * @param deleted    Indicates whether the permission is deleted or not.
 */
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
