package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseBasicDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferItemResponseBasicDTO(
        UUID id,
        BigDecimal cost,
        ProductResponseBasicDTO product,
        short quantity,
        UUID transferID
) implements TransferItemResponseDTO {
}
