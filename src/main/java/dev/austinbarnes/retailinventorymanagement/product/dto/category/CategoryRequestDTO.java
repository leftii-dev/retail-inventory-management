package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * CategoryRequestDTO is a data transfer object that represents the request for a category.
 * It includes fields for the category code, name, description, and discount ID.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param categoryCode the unique code of the category
 * @param name         the name of the category
 * @param description  the description of the category
 * @param discountID   the unique identifier of the discount associated with the category
 */
public record CategoryRequestDTO(
        @NotNull String categoryCode,
        @NotNull @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters") String name,
        @Size(max = 3000, message = "Category description cannot exceed 3000 characters") String description,
        UUID discountID
) {
}
