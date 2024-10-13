package dev.austinbarnes.retailinventorymanagement.category.dto;

import java.util.UUID;

public record CategoryHierarchyResponseDetailDTO(
        UUID id,
        CategoryResponseDTO category,
        CategoryResponseDTO parentCategory,
        boolean deleted
) implements CategoryHierarchyResponseDTO{
}
