package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceivingVoucherRequestDTO(
        @DecimalMin(value = "0.00", message = "Freight cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Freight cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal freightCost,
        @DecimalMin(value = "0.00", message = "Fee cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Fee cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal feeCost,
        @DecimalMin(value = "0.00", message = "Discount percentage cannot be negative")
        @DecimalMax(value = "100.00", message = "Discount percantage cannot exceed 100.00")
        @Digits(integer = 3, fraction = 2)
        BigDecimal discountPercent,
        @FutureOrPresent(message = "Payment discount date cannot be in the past")
        LocalDate paymentDiscountDate,
        @Column(name = "payment_net_date")
        @FutureOrPresent(message = "Payment net date cannot be in the past")
        LocalDate paymentNetDate,
        @Size(max = 3000, message = "Notes cannot exceed 3000 characters")
        String notes,
        UUID purchaseOrderID,
        UUID locationID,
        UUID vendorID,
        UUID statusID
) {
}
