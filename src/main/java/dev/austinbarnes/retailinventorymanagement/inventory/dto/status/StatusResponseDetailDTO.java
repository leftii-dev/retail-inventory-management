package dev.austinbarnes.retailinventorymanagement.inventory.dto.status;

import java.time.Instant;
import java.util.UUID;

public record StatusResponseDetailDTO(
        UUID id,
        String name,
        String description,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements StatusResponseDTO{
}
