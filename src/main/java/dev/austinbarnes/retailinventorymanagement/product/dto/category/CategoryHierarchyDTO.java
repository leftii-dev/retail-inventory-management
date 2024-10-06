package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * Simple Category Hierarchy DTO for end user
 * Used for Request and end-user Response
 *
 * @param id
 * @param categoryID
 * @param parentCategoryID
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryHierarchyDTO(
    UUID id,
    UUID categoryID,
    UUID parentCategoryID
) {
}
