package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * ReceivingVoucherRequestDTO is a Data Transfer Object (DTO) used for creating or updating
 * receiving vouchers.
 * <p>
 * It contains fields for freight cost, fee cost, discount percentage, payment discount date,
 * payment net date, notes, and associated IDs for purchase order, location, vendor, and status.
 *
 * @param freightCost        The cost of freight associated with the receiving voucher.
 * @param feeCost            The additional fees associated with the receiving voucher.
 * @param discountPercent    The discount percentage applied to the receiving voucher.
 * @param paymentDiscountDate The date by which payment must be made to receive a discount.
 * @param paymentNetDate     The date by which the full payment is due.
 * @param notes              Additional notes or comments about the receiving voucher.
 * @param purchaseOrderID    The ID of the associated purchase order.
 * @param locationID         The ID of the associated location.
 * @param vendorID           The ID of the associated vendor.
 * @param statusID           The ID of the associated status.
 */
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
