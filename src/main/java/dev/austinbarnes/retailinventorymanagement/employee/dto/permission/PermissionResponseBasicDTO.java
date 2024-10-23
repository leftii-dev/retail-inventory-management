package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import java.util.UUID;

public record PermissionResponseBasicDTO(
    UUID id,
    String name,
    String description
) implements PermissionResponseDTO {
}
