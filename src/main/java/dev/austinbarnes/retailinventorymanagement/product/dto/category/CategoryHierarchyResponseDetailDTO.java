package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.time.Instant;
import java.util.UUID;


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
