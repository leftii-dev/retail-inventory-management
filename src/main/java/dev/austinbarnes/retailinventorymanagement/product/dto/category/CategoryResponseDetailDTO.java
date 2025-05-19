package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import java.time.Instant;
import java.util.UUID;

/**
 * CategoryResponseDetailDTO is a data transfer object that represents the detailed response for a category.
 * It includes fields for the unique identifier, category code, name, description, timestamps for creation and modification,
 * the user IDs of the creators and modifiers, a discount ID, and an active status.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id          the unique identifier of the category
 * @param categoryCode the code representing the category
 * @param name        the name of the category
 * @param description a brief description of the category
 * @param createdAt   the timestamp when the category was created
 * @param modifiedAt  the timestamp when the category was last modified
 * @param createdBy   the user ID of the creator of the category
 * @param modifiedBy  the user ID of the last modifier of the category
 * @param discountID  the unique identifier of the associated discount
 * @param active      indicates whether the category is active or not
 */
public record CategoryResponseDetailDTO(
        UUID id,
        String categoryCode,
        String name,
        String description,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        UUID discountID,
        boolean active
) implements CategoryResponseDTO{
}
