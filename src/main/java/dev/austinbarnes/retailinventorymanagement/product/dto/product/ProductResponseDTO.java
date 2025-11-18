package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ProductResponseDTO is a data transfer object that represents the response for a product.
 * It includes fields for the unique identifier of the product, its SKU, product code, name, description,
 * price, weight, dimensions, additional details, category ID and name, brand ID and name, and discount ID and name.
 * This DTO is used to transfer data between layers of the application.
 */
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
    UUID categoryID();
    String categoryName();
    UUID brandID();
    String brandName();
    UUID discountID();
    String discountName();
    List<ProductImageResponseDTO> images();

}
