package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * ProductRequestDTO is a data transfer object that represents the request for a product.
 * It includes fields for the SKU, name, description, price, weight, dimensions, additional details,
 * active status, category ID, brand ID, and discount ID.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param sku             the stock keeping unit of the product
 * @param name            the name of the product
 * @param description     the description of the product
 * @param price           the price of the product
 * @param weight          the weight of the product
 * @param dimensions      the dimensions of the product
 * @param additionalDetails additional details about the product
 * @param isActive        whether the product is active or not
 * @param categoryID      the unique identifier of the category
 * @param brandID         the unique identifier of the brand
 * @param discountID      the unique identifier of the discount
 */
public record ProductRequestDTO(
        @Size(max = 20, message = "Sku cannot exceed 20 characters") @NotNull String sku,
        @Size(min = 5, max = 100, message = "Name must be between 5 and 100") @NotNull String name,
        @Size(max = 3000, message = "Description cannot exceed 3000 characters") String description,
        @DecimalMax(value = "9999999999.99", message = "Price cannot exceed $9,999,999,999.99") @DecimalMin(value = "0.01", message = "Price cannot be less than $0.01") @Digits(integer = 10, fraction = 2) BigDecimal price,
        @DecimalMax(value = "999999.99", message = "Weight cannot exceed 999,999.99") @DecimalMin(value = "0.00", message = "Weight cannot be negative") @Digits(integer = 6, fraction = 2) BigDecimal weight,
        Map<String, Object> dimensions,
        Map<String, Object> additionalDetails,
        Boolean isActive,
        UUID categoryID,
        UUID brandID,
        UUID discountID
        ) {
}
