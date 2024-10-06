package dev.austinbarnes.retailinventorymanagement.product.dto.brand;

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
    String name,
    String description
) {
}
