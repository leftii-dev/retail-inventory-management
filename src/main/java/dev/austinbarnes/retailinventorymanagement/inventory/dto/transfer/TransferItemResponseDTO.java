package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * TransferItemResponseDTO is a Data Transfer Object (DTO) used for transferring basic transfer item data.
 * <p>
 * It contains fields for transfer item ID, cost, quantity, and transfer ID.
 * <p>
 * This DTO is used to represent the transfer item data in a simplified format.
 */
public interface TransferItemResponseDTO {
    UUID id();
    BigDecimal cost();
    short quantity();
    UUID transferID();
}
