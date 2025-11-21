package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import java.util.UUID;

public interface ProductImageResponseDTO {
    UUID id();
    UUID productId();
    String imageUrl();
    Integer displayOrder();
    Boolean isDefault();
    String altText();
    String imageType();
    String blurDataUrl();
}
