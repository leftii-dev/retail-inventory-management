package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import java.util.UUID;

public interface PermissionResponseDTO {
    UUID id();
    String name();
    String description();
}
