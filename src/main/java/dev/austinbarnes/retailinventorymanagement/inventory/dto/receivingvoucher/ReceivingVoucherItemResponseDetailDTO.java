package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
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
        ReceivingVoucherResponseDTO receivingVoucher,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) {
}
