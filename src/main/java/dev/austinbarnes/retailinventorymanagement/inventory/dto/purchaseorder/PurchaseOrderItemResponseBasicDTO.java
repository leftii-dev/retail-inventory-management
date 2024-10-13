package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.util.UUID;

public record PurchaseOrderItemResponseBasicDTO(
        UUID id,
        short quantity,
        PurchaseOrderResponseDTO purchaseOrder,
        ProductResponseDTO product
) implements PurchaseOrderItemResponseDTO{
}
