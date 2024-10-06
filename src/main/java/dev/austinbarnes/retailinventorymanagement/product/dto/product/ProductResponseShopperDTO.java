package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import dev.austinbarnes.retailinventorymanagement.category.entity.Category;
import dev.austinbarnes.retailinventorymanagement.product.entity.Brand;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Product Response DTO for Shoppers
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
 * @param isActive
 * @param category
 * @param brand
 * @param discount
 */
public record ProductResponseShopperDTO(
        UUID id,
        String sku,
        String productCode,
        String name,
        String description,
        BigDecimal price,
        BigDecimal weight,
        Map<String, Object> dimensions,
        Map<String, Object> additionalDetails,
        boolean isActive,
        CategoryDTO category,
        BrandDTO brand,
        DiscountDTO discount
) {
}
