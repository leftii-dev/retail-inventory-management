package dev.austinbarnes.retailinventorymanagement.employee.dto.role;

import java.util.UUID;

public record RoleResponseBasicDTO(
    UUID id,
    String name,
    String description
) implements RoleResponseDTO{
}
