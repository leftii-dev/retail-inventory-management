package dev.austinbarnes.retailinventorymanagement.category.dto;

import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseDTO;

import java.util.UUID;

public record CategoryResponseBasicDTO(
        UUID id,
        String categoryCode,
        String name,
        String description,
        DiscountResponseDTO discount
) implements CategoryResponseDTO {
}
