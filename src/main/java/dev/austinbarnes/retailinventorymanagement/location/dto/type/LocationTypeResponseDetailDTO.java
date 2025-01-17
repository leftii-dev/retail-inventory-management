package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import java.time.Instant;
import java.util.UUID;

public record LocationTypeResponseDetailDTO(
        UUID id,
        String name,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements LocationTypeResponseDTO{
}
