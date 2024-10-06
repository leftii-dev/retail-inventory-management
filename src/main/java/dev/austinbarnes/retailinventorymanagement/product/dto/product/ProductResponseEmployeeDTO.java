package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record ProductResponseEmployeeDTO(
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
        boolean deleted
) {
}
