package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ReceivingVoucherItemRequestDTO(
        @Min(value = 1, message = "Quantity must be a positive number")
        @Max(value = 10_000, message = "Quantity cannot exceed 10,000, if you need more, add a separate line item for the excess")
        @NotNull
        short quantity,
        @DecimalMax(value = "100.00", message = "Discount percentage cannot exceed 100.00%")
        @DecimalMin(value = "0.00", message = "Discount percentage cannot be negative")
        @Digits(integer = 3, fraction = 2)
        BigDecimal discountPercentage,
        @Size(max = 50, message = "Discount reason cannot exceed 50 characters")
        String discountReason,
        @DecimalMin(value = "0.00", message = "Unit cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Max unit cost annoy exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal costUnit,
        @DecimalMin(value = "0.00", message = "Line cost total cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Max line cost total cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal costLineTotal,
        UUID productID,
        UUID receivingVoucherID
) {
}
