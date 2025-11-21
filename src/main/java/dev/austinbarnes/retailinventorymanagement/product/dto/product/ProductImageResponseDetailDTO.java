package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import java.time.Instant;
import java.util.UUID;

public record ProductImageResponseDetailDTO(
        UUID id,
        UUID productId,
        String imageUrl,
        Integer displayOrder,
        Boolean isDefault,
        String altText,
        String imageType,
        String blurDataUrl,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements ProductImageResponseDTO{}
