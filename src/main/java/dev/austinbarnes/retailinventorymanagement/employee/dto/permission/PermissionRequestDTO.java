package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * PermissionRequestDTO is a Data Transfer Object (DTO) used for creating or updating
 * permissions.
 * <p>
 * It contains the name and description of the permission.
 *
 * @param name        The name of the permission.
 * @param description The description of the permission.
 */
public record PermissionRequestDTO(
    @NotNull
    @Size(min = 3, max = 60, message = "Name of role must be 3 to 60 characters")
    String name,
    @Size(max = 3000, message = "Description cannot exceed 3000 characters")
    String description
){
}
