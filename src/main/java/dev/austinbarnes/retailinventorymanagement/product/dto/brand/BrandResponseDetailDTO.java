package dev.austinbarnes.retailinventorymanagement.product.dto.brand;

import java.time.Instant;
import java.util.UUID;

/**
 * Brand Response Detail DTO for Admin panel
 *
 * @param id
 * @param name
 * @param description
 * @author Austin Barnes
 * @since 2024
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
