package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.util.UUID;

/**
 * CategoryResponseDTO is a data transfer object that represents the response for a category.
 * It includes fields for the unique identifier of the category, its code, name, description, and discount ID.
 * This DTO is used to transfer data between layers of the application.
 */
public interface CategoryResponseDTO {
    UUID id();
    String categoryCode();
    String name();
    String description();
    UUID discountID();
}
