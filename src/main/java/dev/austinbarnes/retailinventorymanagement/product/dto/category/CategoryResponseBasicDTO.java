package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * Category Response DTO - Simplified for end user needs
 *
 * @param id
 * @param categoryCode
 * @param name
 * @param description

 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryResponseBasicDTO(
        UUID id,
        String categoryCode,
        String name,
        String description,
        UUID discountID
) implements CategoryResponseDTO {
}
