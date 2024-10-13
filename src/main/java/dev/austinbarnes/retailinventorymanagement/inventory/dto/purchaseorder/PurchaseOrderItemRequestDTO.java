package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record PurchaseOrderItemRequestDTO(
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal costUnit,
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal costLineTotal,
        @Min(1)
        @Max(10_000)
        short quantity,
        UUID purchaseOrderID,
        UUID productID
) {
}
