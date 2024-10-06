package dev.austinbarnes.retailinventorymanagement.product.dto.discount;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Discount Response DTO for Admin panel
 * Includes active and deleted values for business purposes
 *
 * @param id
 * @param discountCode
 * @param name
 * @param description
 * @param discountPercentage
 * @param active
 * @param deleted
 */
public record DiscountResponseDetailDTO(
        UUID id,
        String discountCode,
        String name,
        String description,
        BigDecimal discountPercentage,
        boolean active,
        boolean deleted
) {
}
