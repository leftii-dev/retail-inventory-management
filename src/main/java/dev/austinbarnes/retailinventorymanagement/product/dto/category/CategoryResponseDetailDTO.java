package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.time.Instant;
import java.util.UUID;

/**
 * Category Response Detail DTO for use in Admin panel
 * Includes details not available to outside user
 *
 * @param id
 * @param categoryCode
 * @param name
 * @param description
 * @param createdAt
 * @param modifiedAt
 * @param createdByID
 * @param modifiedByID
 * @param discountID
 * @param deleted
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryResponseDetailDTO(
        UUID id,
        String categoryCode,
        String name,
        String description,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        UUID modifiedByID,
        UUID discountID,
        boolean deleted
) {
}
