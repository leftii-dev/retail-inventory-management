package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import java.time.Instant;
import java.util.UUID;

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
