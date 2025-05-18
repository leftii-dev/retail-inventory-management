package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * ProductResponseBasicDTO is a data transfer object that represents the response for a product.
 * It includes fields for the unique identifier of the product, its SKU, product code, name, description,
 * price, weight, dimensions, additional details, category ID and name, brand ID and name, and discount ID and name.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id                the unique identifier of the product
 * @param sku               the stock keeping unit of the product
 * @param productCode       the unique code of the product
 * @param name              the name of the product
 * @param description       the description of the product
 * @param price             the price of the product
 * @param weight            the weight of the product
 * @param dimensions        the dimensions of the product
 * @param additionalDetails additional details about the product
 * @param categoryID        the unique identifier of the category to which the product belongs
 * @param categoryName      the name of the category to which the product belongs
 * @param brandID           the unique identifier of the brand to which the product belongs
 * @param brandName         the name of the brand to which the product belongs
 * @param discountID        the unique identifier of any discount applied to this product
 * @param discountName      the name of any discount applied to this product
 */
public record ProductResponseBasicDTO(
        UUID id,
        String sku,
        String productCode,
        String name,
        String description,
        BigDecimal price,
        BigDecimal weight,
        Map<String, Object> dimensions,
        Map<String, Object> additionalDetails,
        UUID categoryID,
        String categoryName,
        UUID brandID,
        String brandName,
        UUID discountID,
        String discountName
) implements ProductResponseDTO {
}
