package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for Product Images
 * @param productID
 * @param imageUrl
 * @param displayOrder
 * @param isDefault
 * @param altText
 * @param imageType
 */

public record ProductImageRequestDTO(
    @NotNull(message = "Image URL cannot be null")
    @Size(min = 5,  max = 500, message = "Image URL must be between 5 and 500 characters.")
    String imageUrl,
    @NotNull(message = "Display Order cannot be null")
    @Min(value = 0, message = "Display Order must be a non-negative integer")
    Integer displayOrder,
    @NotNull(message = "Default must be set to either True or False")
    boolean isDefault,
    @Size(max = 255, message = "Alt Text must be 255 characters or less")
    String altText,
    @Size(max = 50, message = "Image type must be 50 characters or less")
    String imageType,
    @Size(max = 10000, message = "Blur Data URL must be 10,000 characters or less")
    String blurDataUrl
){}
