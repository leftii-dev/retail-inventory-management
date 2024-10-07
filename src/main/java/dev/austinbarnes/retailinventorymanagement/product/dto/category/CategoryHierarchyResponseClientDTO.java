package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * Simple Category Hierarchy Response DTO for end user
 *
 * @param id
 * @param category
 * @param parentCategory
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryHierarchyResponseClientDTO(
    UUID id,
    CategoryResponseDTO category,
    CategoryResponseDTO parentCategory
) implements CategoryHierarchyResponseDTO {
}
