package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * PurchaseOrderResponseBasicDTO is a Data Transfer Object (DTO) used for transferring basic purchase order data.
 * <p>
 * It contains fields for purchase order ID, expected date, total cost, notes, vendor details, and status details.
 * <p>
 * This DTO is used to represent the purchase order data in a simplified format.
 */
public record PurchaseOrderResponseBasicDTO(
        UUID id,
        LocalDate dateExpected,
        BigDecimal totalCost,
        String notes,
        VendorResponseDTO vendor,
        StatusResponseDTO status
) implements PurchaseOrderResponseDTO{
}
