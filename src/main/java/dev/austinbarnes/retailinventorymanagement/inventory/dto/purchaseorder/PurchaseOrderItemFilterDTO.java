package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record PurchaseOrderItemFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal unitCostEqual,
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal unitCostBelow,
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal unitCostAbove,
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal totalCostEqual,
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal totalCostBelow,
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99, double check costs")
        @Digits(integer = 10, fraction = 2)
        BigDecimal totalCostAbove,
        @Min(value = 1, message = "Quantity cannot be negative")
        @Max(value = 10_000, message = "Quantity cannot exceed 10,000")
        Short qtyEqual,
        @Min(value = 1, message = "Quantity cannot be negative")
        @Max(value = 10_000, message = "Quantity cannot exceed 10,000")
        Short qtyBelow,
        @Min(value = 1, message = "Quantity cannot be negative")
        @Max(value = 10_000, message = "Quantity cannot exceed 10,000")
        Short qtyAbove,
        UUID purchaseOrder,
        UUID product

) implements FilterDTO {
}
