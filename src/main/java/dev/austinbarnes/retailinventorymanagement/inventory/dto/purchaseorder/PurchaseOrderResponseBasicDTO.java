package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PurchaseOrderResponseBasicDTO(
        UUID id,
        LocalDate dateExpected,
        BigDecimal totalCost,
        String notes,
        VendorResponseDTO vendor,
        StatusResponseDTO status
) implements PurchaseOrderResponseDTO{
}
