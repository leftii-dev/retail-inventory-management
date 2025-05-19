package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.time.Instant;
import java.util.UUID;

/**
 * CategoryHierarchyResponseDetailDTO is a data transfer object that represents the detailed response for a category hierarchy.
 * It includes fields for the unique identifier, category ID, parent category ID, timestamps for creation and modification,
 * and the user IDs of the creators and modifiers.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id              the unique identifier of the category hierarchy
 * @param categoryID      the unique identifier of the category
 * @param parentCategoryID the unique identifier of the parent category
 * @param createdAt       the timestamp when the category hierarchy was created
 * @param modifiedAt      the timestamp when the category hierarchy was last modified
 * @param createdBy       the user ID of the creator of the category hierarchy
 * @param modifiedBy      the user ID of the last modifier of the category hierarchy
 * @param active          indicates whether the category hierarchy is active or not
 */
public record CategoryHierarchyResponseDetailDTO(
        UUID id,
        UUID categoryID,
        UUID parentCategoryID,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements CategoryHierarchyResponseDTO{
}
