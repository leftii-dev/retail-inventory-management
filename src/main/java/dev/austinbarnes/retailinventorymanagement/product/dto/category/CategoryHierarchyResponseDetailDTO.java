package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.time.Instant;
import java.util.UUID;


public record CategoryHierarchyResponseDetailDTO(
        UUID id,
        UUID categoryID,
        UUID parentCategoryID,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements CategoryHierarchyResponseDTO{
}
