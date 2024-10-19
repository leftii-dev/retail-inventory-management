package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.util.UUID;

public interface PurchaseOrderItemResponseDTO {
    UUID id();
    short quantity();
    UUID purchaseOrderID();
    ProductResponseDTO product();
}
