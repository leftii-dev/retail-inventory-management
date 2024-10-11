package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface PurchaseOrderResponseDTO {
    UUID id();
    LocalDate dateExpected();
    BigDecimal totalCost();
    String notes();
    VendorResponseDTO vendor();
    StatusResponseDTO status();
}
