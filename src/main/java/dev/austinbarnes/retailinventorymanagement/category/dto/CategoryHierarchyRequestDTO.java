package dev.austinbarnes.retailinventorymanagement.category.dto;

import java.util.UUID;

public record CategoryHierarchyRequestDTO(
        UUID categoryID,
        UUID parentCategoryID
) {
}
