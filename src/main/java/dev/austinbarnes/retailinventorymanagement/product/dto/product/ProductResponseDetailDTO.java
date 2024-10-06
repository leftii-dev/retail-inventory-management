package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Product Response DTO for Admin panel
 * Includes other members for business purposes
 *
 * @param id
 * @param sku
 * @param productCode
 * @param name
 * @param description
 * @param price
 * @param cost
 * @param weight
 * @param dimensions
 * @param additionalDetails
 * @param createdAt
 * @param modifiedAt
 * @param isActive
 * @param category
 * @param employee
 * @param discount
 * @param deleted
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
        boolean isActive,
        CategoryDTO category,
        EmployeeDTO employee,
        DiscountDTO discount,
        boolean deleted
) {
}
