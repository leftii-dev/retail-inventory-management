package dev.austinbarnes.retailinventorymanagement.employee.dto.role;

import java.util.UUID;

public interface RoleResponseDTO {
    UUID id();
    String name();
    String description();
}
