package dev.austinbarnes.retailinventorymanagement.inventory.dto.status;

import java.time.Instant;
import java.util.UUID;

/**
 * StatusResponseDetailDTO is a Data Transfer Object (DTO) used for transferring detailed status data.
 * <p>
 * It contains fields for status ID, name, description, timestamps for creation and modification,
 * and user IDs for the creator and modifier.
 * <p>
 * This DTO is used to represent the status data in a detailed format.
 */
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
