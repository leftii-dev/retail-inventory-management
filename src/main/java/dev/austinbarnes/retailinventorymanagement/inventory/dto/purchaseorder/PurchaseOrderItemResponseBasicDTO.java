package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.util.UUID;

/**
 * PurchaseOrderItemResponseBasicDTO is a Data Transfer Object (DTO) used for transferring basic purchase order item data.
 * <p>
 * It contains fields for purchase order item ID, quantity, purchase order ID, and product details.
 * <p>
 * This DTO is used to represent the purchase order item data in a simplified format.
 */
public record PurchaseOrderItemResponseBasicDTO(
        UUID id,
        short quantity,
        UUID purchaseOrderID,
        ProductResponseDTO product
) implements PurchaseOrderItemResponseDTO{
}
