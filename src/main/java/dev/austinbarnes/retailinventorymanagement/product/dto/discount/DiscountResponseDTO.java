package dev.austinbarnes.retailinventorymanagement.product.dto.discount;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO interface for Discount
 * Allows return type safety in controller with different DTO records
 *
 * @author Austin Barnes
 * @since 2024
 * */
public interface DiscountResponseDTO {
    UUID id();
    String discountCode();
    String name();
    String description();
    BigDecimal discountPercentage();
}
