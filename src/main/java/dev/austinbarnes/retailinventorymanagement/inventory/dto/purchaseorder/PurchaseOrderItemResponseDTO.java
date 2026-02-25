package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * PurchaseOrderItemResponseBasicDTO is a Data Transfer Object (DTO) used for transferring basic purchase order item data.
 * <p>
 * It contains fields for purchase order item ID, quantity, purchase order ID, and product details.
 * <p>
 * This DTO is used to represent the purchase order item data in a simplified format.
 */
public interface PurchaseOrderItemResponseDTO {
    UUID id();
    short quantity();
    BigDecimal costUnit();
    BigDecimal costLineTotal();
    UUID purchaseOrderID();
    ProductResponseDTO product();
}
