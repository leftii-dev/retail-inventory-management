package dev.austinbarnes.retailinventorymanagement.product.dto.brand;

import java.time.Instant;
import java.util.UUID;

/**
 * BrandResponseDetailDTO is a data transfer object that represents the detailed response for a brand.
 * It includes fields for the unique identifier, name, description, creation and modification timestamps,
 * creator and modifier identifiers, and active status of the brand.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id          the unique identifier of the brand
 * @param name        the name of the brand
 * @param description the description of the brand
 * @param createdAt   the timestamp when the brand was created
 * @param modifiedAt  the timestamp when the brand was last modified
 * @param createdBy   the unique identifier of the user who created the brand
 * @param modifiedBy  the unique identifier of the user who last modified the brand
 * @param active      indicates whether the brand is active or not
 */
public record BrandResponseDetailDTO(
        UUID id,
        String name,
        String description,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements BrandResponseDTO {
}
