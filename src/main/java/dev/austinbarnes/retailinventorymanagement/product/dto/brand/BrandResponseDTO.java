package dev.austinbarnes.retailinventorymanagement.product.dto.brand;

import java.util.UUID;

/**
 * DTO interface for Products
 * Allows return type safety in controller with different DTO records
 *
 * @author Austin Barnes
 * @since 2024
 */
public interface BrandResponseDTO {
    UUID id();

    String name();

    String description();
}
