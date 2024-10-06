package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * Category Hierarchy Detail DTO for Admin panel
 * Includes deleted value
 *
 * @param id
 * @param categoryID
 * @param parentCategoryID
 * @param deleted
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryHierarchyResponseDetailDTO(
        UUID id,
        UUID categoryID,
        UUID parentCategoryID,
        boolean deleted
) {
}
