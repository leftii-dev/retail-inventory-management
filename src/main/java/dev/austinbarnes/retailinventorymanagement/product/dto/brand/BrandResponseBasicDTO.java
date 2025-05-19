package dev.austinbarnes.retailinventorymanagement.product.dto.brand;

import java.util.UUID;

/**
 * BrandResponseBasicDTO is a data transfer object that represents the basic response for a brand.
 * It includes fields for the unique identifier, name, and description of the brand.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id          the unique identifier of the brand
 * @param name        the name of the brand
 * @param description the description of the brand
 */
public record BrandResponseBasicDTO(
        UUID id,
        String name,
        String description
) implements BrandResponseDTO {
}
