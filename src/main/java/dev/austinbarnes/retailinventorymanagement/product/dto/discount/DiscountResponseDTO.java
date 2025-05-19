package dev.austinbarnes.retailinventorymanagement.product.dto.discount;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DiscountResponseBasicDTO is a data transfer object that represents the response for a discount.
 * It includes fields for the unique identifier of the discount, its code, name, description, and discount percentage.
 * This DTO is used to transfer data between layers of the application.
 */
public interface DiscountResponseDTO {
    UUID id();
    String discountCode();
    String name();
    String description();
    BigDecimal discountPercentage();
}
