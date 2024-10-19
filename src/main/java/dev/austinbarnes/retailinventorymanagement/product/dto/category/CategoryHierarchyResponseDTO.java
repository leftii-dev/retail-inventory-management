package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * DTO interface for CategoryHierarchy
 * Allows return type safety in controller with different DTO records
 *
 * @author Austin Barnes
 * @since 2024
 * */
public interface CategoryHierarchyResponseDTO {
    UUID id();
    UUID categoryID();
    UUID parentCategoryID();
}
