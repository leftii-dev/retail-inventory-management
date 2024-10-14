package dev.austinbarnes.retailinventorymanagement.product.dto.brand;

import java.util.UUID;

/**
 * Brand Response DTO for end-user
 *
 * @param id
 * @param name
 * @param description
 * @author Austin Barnes
 * @since 2024
 */
public record BrandResponseBasicDTO(
        UUID id,
        String name,
        String description
) implements BrandResponseDTO {
}
