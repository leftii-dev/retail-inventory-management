package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * ReceivingVoucherItemResponseDetailDTO is a Data Transfer Object (DTO) used for transferring detailed receiving voucher item data.
 * <p>
 * It contains fields for receiving voucher item ID, quantity, discount percentage, discount reason, cost unit, cost line total,
 * creation and modification timestamps, product details, receiving voucher ID, creator and modifier IDs, and active status.
 * <p>
 * This DTO is used to represent the receiving voucher item data in a detailed format.
 */
public record ReceivingVoucherItemResponseDetailDTO(
        UUID id,
        short quantity,
        BigDecimal discountPercentage,
        String discountReason,
        BigDecimal costUnit,
        BigDecimal costLineTotal,
        Instant createdAt,
        Instant modifiedAt,
        ProductResponseDTO product,
        String notes,
        UUID receivingVoucherID,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements ReceivingVoucherItemResponseDTO{
}
