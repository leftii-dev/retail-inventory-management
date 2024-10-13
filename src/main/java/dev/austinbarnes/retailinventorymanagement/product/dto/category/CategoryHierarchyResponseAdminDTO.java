package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * Category Hierarchy Detail DTO for Admin panel
 * Includes deleted value
 *
 * @param id
 * @param category
 * @param parentCategory
 * @param deleted
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryHierarchyResponseAdminDTO(
        UUID id,
        CategoryResponseDTO category,
        CategoryResponseDTO parentCategory,
        boolean deleted
) implements CategoryHierarchyResponseDTO{
}
