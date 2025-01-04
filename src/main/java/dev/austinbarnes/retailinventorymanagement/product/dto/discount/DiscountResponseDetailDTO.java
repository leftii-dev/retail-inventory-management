package dev.austinbarnes.retailinventorymanagement.product.dto.discount;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record DiscountResponseDetailDTO(
        UUID id,
        String discountCode,
        String name,
        String description,
        BigDecimal discountPercentage,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean active
) implements DiscountResponseDTO {
}
