package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

public interface ReceivingVoucherItemResponseDTO {
    UUID id();
    short quantity();
    BigDecimal discountPercentage();
    String discountReason();
    BigDecimal costUnit();
    BigDecimal costLineTotal();
    ProductResponseDTO product();
    UUID receivingVoucherID();
}
