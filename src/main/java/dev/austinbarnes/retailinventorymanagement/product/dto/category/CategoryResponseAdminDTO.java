package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.time.Instant;
import java.util.UUID;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseDTO;

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
 * @param discount
 * @param deleted
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryResponseAdminDTO(
        UUID id,
        String categoryCode,
        String name,
        String description,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        UUID modifiedByID,
        DiscountResponseDTO discount,
        boolean deleted
) implements CategoryResponseDTO{
}
