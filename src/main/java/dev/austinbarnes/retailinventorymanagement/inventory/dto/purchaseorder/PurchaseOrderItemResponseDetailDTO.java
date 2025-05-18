package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * PurchaseOrderItemResponseDetailDTO is a Data Transfer Object (DTO) used for transferring detailed purchase order item data.
 * <p>
 * It contains fields for purchase order item ID, cost per unit, total cost, quantity, timestamps for creation and modification,
 * purchase order ID, product details, user IDs for the creator and modifier, and an active status.
 * <p>
 * This DTO is used to represent the purchase order item data in a detailed format.
 */
public record PurchaseOrderItemResponseDetailDTO(
        UUID id,
        BigDecimal costUnit,
        BigDecimal costLineTotal,
        short quantity,
        Instant createdAt,
        Instant modifiedAt,
        UUID purchaseOrderID,
        ProductResponseDTO product,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements PurchaseOrderItemResponseDTO{
}
