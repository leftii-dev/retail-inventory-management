package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseClientDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseDTO;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Product Response DTO for users
 *
 * @param id
 * @param sku
 * @param productCode
 * @param name
 * @param description
 * @param price
 * @param weight
 * @param dimensions
 * @param additionalDetails
 * @param category
 * @param brand
 * @param discount
 */
public record ProductResponseClientDTO(
        UUID id,
        String sku,
        String productCode,
        String name,
        String description,
        BigDecimal price,
        BigDecimal weight,
        Map<String, Object> dimensions,
        Map<String, Object> additionalDetails,
        CategoryResponseDTO category,
        BrandResponseDTO brand,
        DiscountResponseDTO discount
) implements ProductResponseDTO {
}
