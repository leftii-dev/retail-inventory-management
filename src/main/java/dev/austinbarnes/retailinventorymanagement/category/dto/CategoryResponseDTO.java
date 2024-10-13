package dev.austinbarnes.retailinventorymanagement.category.dto;

import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseDTO;

import java.util.UUID;

public interface CategoryResponseDTO {
    UUID id();
    String categoryCode();
    String name();
    String description();
    DiscountResponseDTO discount();
}
