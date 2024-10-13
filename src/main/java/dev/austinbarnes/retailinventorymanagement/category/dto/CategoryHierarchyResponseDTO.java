package dev.austinbarnes.retailinventorymanagement.category.dto;

import java.util.UUID;

public interface CategoryHierarchyResponseDTO {
    UUID id();
    CategoryResponseDTO category();
    CategoryResponseDTO parentCategory();
}
