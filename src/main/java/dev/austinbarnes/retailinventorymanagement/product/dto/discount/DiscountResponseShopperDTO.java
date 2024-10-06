package dev.austinbarnes.retailinventorymanagement.product.dto.discount;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Discount Response DTO for Shoppers
 * Excludes active and deleted values as Shoppers should only receive active/non-deleted items
 *
 * @param id
 * @param discountCode
 * @param name
 * @param description
 * @param discountPercentage
 */
public record DiscountResponseShopperDTO(
        UUID id,
        String discountCode,
        String name,
        String description,
        BigDecimal discountPercentage
) {
}
