package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

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
        UUID categoryID,
        String categoryName,
        UUID brandID,
        String brandName,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        UUID discountID,
        String discountName,
        boolean deleted
) implements ProductResponseDTO {
}
