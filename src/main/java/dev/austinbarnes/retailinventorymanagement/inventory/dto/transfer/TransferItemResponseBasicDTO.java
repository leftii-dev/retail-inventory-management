package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseBasicDTO;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * TransferItemResponseBasicDTO is a Data Transfer Object (DTO) used for transferring basic transfer item data.
 * <p>
 * It contains fields for transfer item ID, cost, product details, quantity, and transfer ID.
 * <p>
 * This DTO is used to represent the transfer item data in a simplified format.
 */
public record TransferItemResponseBasicDTO(
        UUID id,
        BigDecimal cost,
        ProductResponseBasicDTO product,
        short quantity,
        UUID transferID
) implements TransferItemResponseDTO {
}
