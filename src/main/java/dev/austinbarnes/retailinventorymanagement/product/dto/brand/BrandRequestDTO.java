package dev.austinbarnes.retailinventorymanagement.product.dto.brand;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * BrandRequestDTO is a data transfer object that represents the request for a brand.
 * It includes fields for the name and description of the brand.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param name        the name of the brand
 * @param description the description of the brand
 */
public record BrandRequestDTO(
    @Size(min = 1, max = 50, message = "Brand name must be between 1 and 50 characters") @NotNull String name,
    @Size(max = 3000, message = "Description cannot exceed 3000 characters") String description
) {
}
