package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

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
        UUID receivingVoucherID,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements ReceivingVoucherItemResponseDTO{
}
