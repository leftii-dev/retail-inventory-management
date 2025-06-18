package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ReceivingVoucherItemFIlterDTO(
        BaseFilterDTO baseFilterDTO,
        @Min(value = 1, message = "Quantity must be a positive number")
        @Max(value = 10_000, message = "Quantity cannot exceed 10,000, if you need more, add a separate line item for the excess")
        Short qty,
        @Min(value = 1, message = "Quantity must be a positive number")
        @Max(value = 10_000, message = "Quantity cannot exceed 10,000, if you need more, add a separate line item for the excess")
        Short qtyBelow,
        @Min(value = 1, message = "Quantity must be a positive number")
        @Max(value = 10_000, message = "Quantity cannot exceed 10,000, if you need more, add a separate line item for the excess")
        Short qtyAbove,
        @DecimalMin(value = "0.00", message = "Line cost total cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Max line cost total cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal costTotal,
        @DecimalMin(value = "0.00", message = "Line cost total cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Max line cost total cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal costTotalBelow,
        @DecimalMin(value = "0.00", message = "Line cost total cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Max line cost total cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal costTotalAbove,
        UUID product,
        UUID receivingVoucher
        ) implements FilterDTO {
}
