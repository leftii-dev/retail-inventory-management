package dev.austinbarnes.retailinventorymanagement.product.dto.discount;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DiscountResponseDetailDTO is a data transfer object that represents the response for a discount.
 * It includes fields for the unique identifier of the discount, its code, name, description, discount percentage,
 * creation and modification timestamps, creator and modifier IDs, and active status.
 * This DTO is used to transfer data between layers of the application.
 *
 * @param id                the unique identifier of the discount
 * @param discountCode      the unique code of the discount
 * @param name              the name of the discount
 * @param description       the description of the discount
 * @param discountPercentage the percentage of the discount
 * @param createdAt         the timestamp when the discount was created
 * @param modifiedAt        the timestamp when the discount was last modified
 * @param createdBy         the unique identifier of the user who created the discount
 * @param modifiedBy        the unique identifier of the user who last modified the discount
 * @param active            whether the discount is currently active or not
 */
public record DiscountResponseDetailDTO(
        UUID id,
        String discountCode,
        String name,
        String description,
        BigDecimal discountPercentage,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements DiscountResponseDTO {
}
