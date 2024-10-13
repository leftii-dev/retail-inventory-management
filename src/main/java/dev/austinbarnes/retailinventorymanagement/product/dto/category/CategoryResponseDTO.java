package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseDTO;

import java.util.UUID;

/**
 * DTO interface for Category
 * Allows return type safety in controller with different DTO records
 *
 * @author Austin Barnes
 * @since 2024
 * */
public interface CategoryResponseDTO {
    UUID id();
    String categoryCode();
    String name();
    String description();
    DiscountResponseDTO discount();
}
