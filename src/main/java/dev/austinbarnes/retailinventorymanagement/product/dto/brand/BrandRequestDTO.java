package dev.austinbarnes.retailinventorymanagement.product.dto.brand;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Brand Request DTO for creating and updating Brand entries
 *
 * @param name
 * @param description
 *
 * @author Austin Barnes
 * @since 2024
 * */
public record BrandRequestDTO(
    @Size(min = 1, max = 50, message = "Brand name must be between 1 and 50 characters") @NotNull String name,
    @Size(max = 3000, message = "Description cannot exceed 3000 characters") String description
) {
}
