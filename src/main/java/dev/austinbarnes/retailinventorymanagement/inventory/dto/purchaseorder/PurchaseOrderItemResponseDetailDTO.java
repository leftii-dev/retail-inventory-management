package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PurchaseOrderItemResponseDetailDTO(
        UUID id,
        BigDecimal costUnit,
        BigDecimal costLineTotal,
        short quantity,
        Instant createdAt,
        Instant modifiedAt,
        UUID purchaseOrderID,
        ProductResponseDTO product,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements PurchaseOrderItemResponseDTO{
}
