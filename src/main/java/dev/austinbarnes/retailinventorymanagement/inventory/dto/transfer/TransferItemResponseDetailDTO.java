package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * TransferItemResponseDetailDTO is a Data Transfer Object (DTO) used for transferring detailed transfer item data.
 * <p>
 * It contains fields for transfer item ID, cost, product details, quantity, transfer ID, and timestamps.
 * <p>
 * This DTO is used to represent the transfer item data in a detailed format.
 */
public record TransferItemResponseDetailDTO(
        UUID id,
        BigDecimal cost,
        short quantity,
        Instant createdAt,
        Instant modifiedAt,
        UUID transferID,
        ProductResponseDTO product,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements TransferItemResponseDTO {
}
