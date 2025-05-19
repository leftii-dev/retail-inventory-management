package dev.austinbarnes.retailinventorymanagement.product.dto.brand;

import java.util.UUID;

/**
 * BrandResponseBasicDTO is a data transfer object that represents the basic response for a brand.
 * It includes fields for the unique identifier, name, and description of the brand.
 * This DTO is used to transfer data between layers of the application.
 */
public interface BrandResponseDTO {
    UUID id();

    String name();

    String description();
}
