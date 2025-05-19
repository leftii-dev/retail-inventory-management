package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import java.time.Instant;
import java.util.UUID;

/**
 * PermissionResponseDetailDTO is a Data Transfer Object (DTO) used for representing
 * detailed information about permissions.
 * <p>
 * It contains the ID, name, description, creation and modification timestamps, and
 * the IDs of the users who created and modified the permission.
 *
 * @param id          The ID of the permission.
 * @param name        The name of the permission.
 * @param description A brief description of the permission.
 * @param createdAt   The timestamp when the permission was created.
 * @param modifiedAt  The timestamp when the permission was last modified.
 * @param createdBy   The ID of the user who created the permission.
 * @param modifiedBy  The ID of the user who last modified the permission.
 * @param deleted     Indicates whether the permission is deleted or not.
 */
public record PermissionResponseDetailDTO(
        UUID id,
        String name,
        String description,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean deleted
) implements PermissionResponseDTO {
}
