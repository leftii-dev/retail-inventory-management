package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * ReceivingVoucherItemResponseBasicDTO is a Data Transfer Object (DTO) used for transferring basic receiving voucher item data.
 * <p>
 * It contains fields for receiving voucher item ID, quantity, discount percentage, discount reason, cost unit, cost line total,
 * product details, and receiving voucher ID.
 * <p>
 * This DTO is used to represent the receiving voucher item data in a simplified format.
 */
public record ReceivingVoucherItemResponseBasicDTO(
        UUID id,
        short quantity,
        BigDecimal discountPercentage,
        String discountReason,
        BigDecimal costUnit,
        BigDecimal costLineTotal,
        ProductResponseDTO product,
        UUID receivingVoucherID,
        String notes
) implements ReceivingVoucherItemResponseDTO {
}
