package dev.austinbarnes.retailinventorymanagement.common;

import java.time.Instant;
import java.util.UUID;

public record BaseFilterDTO(
        Instant createdAt,
        Instant createdBefore,
        Instant createdAfter,
        Instant modifiedAt,
        Instant modifiedBefore,
        Instant modifiedAfter,
        UUID createdBy,
        UUID modifiedBy,
        Boolean showInactive
){}
