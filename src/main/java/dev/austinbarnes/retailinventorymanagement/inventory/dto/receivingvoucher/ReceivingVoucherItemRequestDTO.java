package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * ReceivingVoucherItemRequestDTO is a Data Transfer Object (DTO) used for creating or updating
 * receiving voucher items.
 * <p>
 * It contains fields for quantity, discount percentage, discount reason, and unit cost,
 * product ID, and receiving voucher ID.
 * Line total is derived by the backend from unit cost and quantity.
 *
 * @param quantity          The quantity of the item.
 * @param discountPercentage The discount percentage applied to the item.
 * @param discountReason     The reason for the discount.
 * @param costUnit          The unit cost of the item.
 * @param productID         The ID of the product associated with the item.
 * @param receivingVoucherID The ID of the receiving voucher associated with the item.
 */
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
        UUID productID,
        UUID receivingVoucherID
) {
}
