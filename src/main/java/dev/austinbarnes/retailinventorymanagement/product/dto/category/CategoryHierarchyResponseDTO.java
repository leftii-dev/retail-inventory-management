package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * CategoryHierarchyRequestDTO is a data transfer object that represents the request for a category hierarchy.
 * It includes fields for the unique identifier of the category and the parent category.
 * This DTO is used to transfer data between layers of the application.
 */
public interface CategoryHierarchyResponseDTO {
    UUID id();
    UUID categoryID();
    UUID parentCategoryID();
}
