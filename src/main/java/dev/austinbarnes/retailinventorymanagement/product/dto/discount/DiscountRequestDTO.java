package dev.austinbarnes.retailinventorymanagement.product.dto.discount;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Discount Request DTO used for creating and updating Discounts
 *
 * @param discountCode
 * @param name
 * @param description
 * @param discountPercentage
 * @param active
 */
public record DiscountRequestDTO(
        @NotNull @Size(min = 6, max = 30, message = "Discount code must be between 6 and 30 characters") String discountCode,
        @NotNull @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters") String name,
        @Size(max = 3000, message = "Description cannot exceed 3000 characters") String description,
        @NotNull @DecimalMin(value = "0.00", message = "Discount percentage cannot be negative") @DecimalMax(value = "100.00", message = "Discount percentage cannot exceed 100%") @Digits(integer = 3, fraction = 2) BigDecimal discountPercentage,
        Boolean active
) {
}
