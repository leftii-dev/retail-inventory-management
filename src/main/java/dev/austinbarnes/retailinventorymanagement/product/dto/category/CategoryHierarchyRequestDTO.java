package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * Category Hierarchy Request DTO for creating and updating Category Hierarchy entries
 * @param categoryID
 * @param parentCategoryID
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryHierarchyRequestDTO(
        UUID categoryID,
        UUID parentCategoryID
) {
}
