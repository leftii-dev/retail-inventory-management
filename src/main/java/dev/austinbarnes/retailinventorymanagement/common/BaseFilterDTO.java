package dev.austinbarnes.retailinventorymanagement.common;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

import java.time.Instant;
import java.util.UUID;

public record BaseFilterDTO(
        @PastOrPresent(message = "createdAt must be past or present.")
        Instant createdAt,
        @Past(message = "createdBefore must be past.")
        Instant createdBefore,
        @Future(message = "createdAfter must be future.")
        Instant createdAfter,
        @PastOrPresent(message = "modifiedAt must be past or present.")
        Instant modifiedAt,
        @Past(message = "modifiedBefore must be past")
        Instant modifiedBefore,
        @Future(message = "modifiedAfter must be future.")
        Instant modifiedAfter,
        UUID createdBy,
        UUID modifiedBy,
        Boolean showInactive
){}
