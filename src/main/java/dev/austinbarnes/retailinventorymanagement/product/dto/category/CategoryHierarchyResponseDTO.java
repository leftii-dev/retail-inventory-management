package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * Simple Category Hierarchy Response DTO for end user
 *
 * @param id
 * @param categoryID
 * @param parentCategoryID
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryHierarchyResponseDTO(
    UUID id,
    UUID categoryID,
    UUID parentCategoryID
) {
}
