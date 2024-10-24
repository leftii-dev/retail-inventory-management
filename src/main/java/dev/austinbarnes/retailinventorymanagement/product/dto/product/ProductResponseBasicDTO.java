package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;


public record ProductResponseBasicDTO(
        UUID id,
        String sku,
        String productCode,
        String name,
        String description,
        BigDecimal price,
        BigDecimal weight,
        Map<String, Object> dimensions,
        Map<String, Object> additionalDetails,
        UUID categoryID,
        String categoryName,
        UUID brandID,
        String brandName,
        UUID discountID,
        String discountName
) implements ProductResponseDTO {
}
