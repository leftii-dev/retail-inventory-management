package dev.austinbarnes.retailinventorymanagement.category.dto;

import java.util.UUID;

public record CategoryHierarchyResponseBasicDTO(
        UUID id,
        CategoryResponseDTO category,
        CategoryResponseDTO parentCategory
) implements CategoryHierarchyResponseDTO {
}
