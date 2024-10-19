package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * Category Hierarchy Detail DTO for Admin panel
 * Includes deleted value
 *
 * @param id
 * @param category
 * @param parentCategory
 * @param deleted
 *
 * @author Austin Barnes
 * @since 2024
 * */
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
