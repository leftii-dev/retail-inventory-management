package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * ProductResponseDetailDTO is a data transfer object that represents the response for a product.
 * It includes fields for the unique identifier of the product, its SKU, product code, name, description,
 * price, cost, weight, dimensions, additional details, creation and modification timestamps,
 * category ID and name, brand ID and name, creator and modifier IDs, discount ID and name, and active status.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id                the unique identifier of the product
 * @param sku               the stock keeping unit of the product
 * @param productCode       the unique code of the product
 * @param name              the name of the product
 * @param description       the description of the product
 * @param price             the price of the product
 * @param cost              the cost of the product
 * @param weight            the weight of the product
 * @param dimensions        additional dimensions of the product
 * @param additionalDetails additional details about the product
 * @param createdAt         the timestamp when the product was created
 * @param modifiedAt        the timestamp when the product was last modified
 * @param categoryID        the unique identifier of the category to which this product belongs
 * @param categoryName      the name of the category to which this product belongs
 * @param brandID           the unique identifier of the brand to which this product belongs
 * @param brandName         the name of the brand to which this product belongs
 * @param createdBy         the unique identifier of the user who created this product
 * @param modifiedBy        the unique identifier of the user who last modified this product
 * @param discountID        optional unique identifier for any applicable discount on this product
 * @param discountName      optional name for any applicable discount on this product
 */
public record ProductResponseDetailDTO(
        UUID id,
        String sku,
        String productCode,
        String name,
        String description,
        BigDecimal price,
        BigDecimal cost,
        BigDecimal weight,
        Map<String, Object> dimensions,
        Map<String, Object> additionalDetails,
        Instant createdAt,
        Instant modifiedAt,
        UUID categoryID,
        String categoryName,
        UUID brandID,
        String brandName,
        UUID createdBy,
        UUID modifiedBy,
        UUID discountID,
        String discountName,
        boolean active
) implements ProductResponseDTO {
}
