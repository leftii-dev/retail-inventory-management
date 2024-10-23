package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PermissionRequestDTO(
    @NotNull
    @Size(min = 3, max = 60, message = "Name of role must be 3 to 60 characters")
    String name,
    @Size(max = 3000, message = "Description cannot exceed 3000 characters")
    String description
){
}
