package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * Category Response DTO - Simplified for end user needs
 *
 * @param id
 * @param categoryCode
 * @param name
 * @param description
 * @param discountID
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryResponseDTO(
        UUID id,
        String categoryCode,
        String name,
        String description,
        UUID discountID
) {
}
