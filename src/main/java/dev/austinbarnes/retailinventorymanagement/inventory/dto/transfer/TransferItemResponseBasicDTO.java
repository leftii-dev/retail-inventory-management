package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferItemResponseBasicDTO(
        UUID id,
        BigDecimal cost,
        short quantity,
        TransferResponseDTO transfer
) implements TransferItemResponseDTO {
}
