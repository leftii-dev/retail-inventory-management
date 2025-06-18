package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceivingVoucherFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 1, max = 10, message = "Receiving Voucher Code search must be between 1 and 10 characters")
        String codeContains,
        @DecimalMin(value = "0.00", message = "Freight cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Freight cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal freightEqual,
        @DecimalMin(value = "0.00", message = "Freight cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Freight cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal freightBelow,
        @DecimalMin(value = "0.00", message = "Freight cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Freight cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal freightAbove,
        @DecimalMin(value = "0.00", message = "Fee cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Fee cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal feeEqual,
        @DecimalMin(value = "0.00", message = "Fee cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Fee cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal feeBelow,
        @DecimalMin(value = "0.00", message = "Fee cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Fee cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal feeAbove,
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Total cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal totalEqual,
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Total cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal totalBelow,
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Total cost cannot exceed $9,999,999,999.99")
        @Digits(integer = 10, fraction = 2)
        BigDecimal totalAbove,
        @DecimalMin(value = "0.00", message = "Total discount cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Discount cannot be greater than total cost")
        @Digits(integer = 10, fraction = 2)
        BigDecimal discountEqual,
        @DecimalMin(value = "0.00", message = "Total discount cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Discount cannot be greater than total cost")
        @Digits(integer = 10, fraction = 2)
        BigDecimal discountBelow,
        @DecimalMin(value = "0.00", message = "Total discount cannot be negative")
        @DecimalMax(value = "9999999999.99", message = "Discount cannot be greater than total cost")
        @Digits(integer = 10, fraction = 2)
        BigDecimal discountAbove,
        @DecimalMin(value = "0.00", message = "Discount percentage cannot be negative")
        @DecimalMax(value = "100.00", message = "Discount percentage cannot exceed 100.00")
        @Digits(integer = 3, fraction = 2)
        BigDecimal discountPercentEqual,
        @DecimalMin(value = "0.00", message = "Discount percentage cannot be negative")
        @DecimalMax(value = "100.00", message = "Discount percentage cannot exceed 100.00")
        @Digits(integer = 3, fraction = 2)
        BigDecimal discountPercentBelow,
        @DecimalMin(value = "0.00", message = "Discount percentage cannot be negative")
        @DecimalMax(value = "100.00", message = "Discount percentage cannot exceed 100.00")
        @Digits(integer = 3, fraction = 2)
        BigDecimal discountPercentAbove,
        LocalDate discountOn,
        LocalDate discountBefore,
        LocalDate discountAfter,
        LocalDate dueOn,
        LocalDate dueAfter,
        LocalDate dueBefore,
        @Size(min = 5, max = 50, message = "Notes search must be between 5 and 50 characters")
        String notesContain,
        UUID purchaseOrder,
        UUID location,
        UUID vendor,
        UUID status
) implements FilterDTO {
}
