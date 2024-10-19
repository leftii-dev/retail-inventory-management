package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransferItemResponseDTO {
    UUID id();
    BigDecimal cost();
    short quantity();
    UUID transferID();
}
