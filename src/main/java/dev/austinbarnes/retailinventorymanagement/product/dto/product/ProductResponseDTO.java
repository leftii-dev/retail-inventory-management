package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * DTO interface for Products
 * Allows return type safety in controller with different DTO records
 *
 * @author Austin Barnes
 * @since 2024
 * */
public interface ProductResponseDTO {
    UUID id();
    String sku();
    String productCode();
    String name();
    String description();
    BigDecimal price();
    BigDecimal weight();
    Map<String, Object> dimensions();
    Map<String, Object> additionalDetails();
    CategoryDTO category();
    BrandDTO brand();
    DiscountDTO discount();
}
