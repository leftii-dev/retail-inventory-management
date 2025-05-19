package dev.austinbarnes.retailinventorymanagement.product.dto.discount;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DiscountRequestDTO is a data transfer object that represents the request for a discount.
 * It includes fields for the discount code, name, description, discount percentage, and active status.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param discountCode      the unique code of the discount
 * @param name              the name of the discount
 * @param description       the description of the discount
 * @param discountPercentage the percentage of the discount
 * @param active            whether the discount is active or not
 */
public record DiscountRequestDTO(
        @NotNull @Size(min = 6, max = 30, message = "Discount code must be between 6 and 30 characters") String discountCode,
        @NotNull @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters") String name,
        @Size(max = 3000, message = "Description cannot exceed 3000 characters") String description,
        @NotNull @DecimalMin(value = "0.00", message = "Discount percentage cannot be negative") @DecimalMax(value = "100.00", message = "Discount percentage cannot exceed 100%") @Digits(integer = 3, fraction = 2) BigDecimal discountPercentage,
        Boolean active
) {
}
