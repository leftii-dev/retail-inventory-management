package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * PurchaseOrderResponseDetailDTO is a Data Transfer Object (DTO) used for transferring detailed purchase order data.
 * <p>
 * It contains fields for purchase order ID, purchase order code, expected date, total cost, notes, vendor details,
 * timestamps for creation and modification, user IDs for the creator and modifier, status details, and an active status.
 * <p>
 * This DTO is used to represent the purchase order data in a detailed format.
 */
public record PurchaseOrderResponseDetailDTO(
        UUID id,
        String purchaseOrderCode,
        LocalDate dateExpected,
        BigDecimal totalCost,
        Instant createdAt,
        Instant modifiedAt,
        String notes,
        VendorResponseDTO vendor,
        UUID createdByID,
        UUID modifiedByID,
        StatusResponseDTO status,
        boolean active
) implements PurchaseOrderResponseDTO{
}
