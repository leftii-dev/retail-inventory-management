package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Category Request DTO used to create and update Category entries
 *
 * @param categoryCode
 * @param name
 * @param description
 * @param discountID
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record CategoryRequestDTO(
        @NotNull String categoryCode,
        @NotNull @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters") String name,
        @Size(max = 3000, message = "Category description cannot exceed 3000 characters") String description,
        UUID discountID
) {
}
