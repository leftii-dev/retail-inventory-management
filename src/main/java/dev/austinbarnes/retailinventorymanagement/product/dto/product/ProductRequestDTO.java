package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Product Request DTO for creating and updating Products
 *
 * @param sku
 * @param name
 * @param description
 * @param price
 * @param weight
 * @param dimensions
 * @param additionalDetails
 * @param isActive
 * @param categoryID
 * @param brandID
 * @param discountID
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
