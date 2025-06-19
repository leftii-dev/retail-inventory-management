package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferItemFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost exceeds limit ($9999999999.99), double check costs")
        BigDecimal cost,
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost exceeds limit ($9999999999.99), double check costs")
        BigDecimal costBelow,
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost exceeds limit ($9999999999.99), double check costs")
        BigDecimal costAbove,
        @Min(value = 1, message = "Minimum transfer quantity is one (1)")
        @Max(value = 10000, message = "Maximum transfer wuantity per line is 10,000")
        Short qty,
        @Min(value = 1, message = "Minimum transfer quantity is one (1)")
        @Max(value = 10000, message = "Maximum transfer wuantity per line is 10,000")
        Short qtyBelow,
        @Min(value = 1, message = "Minimum transfer quantity is one (1)")
        @Max(value = 10000, message = "Maximum transfer wuantity per line is 10,000")
        Short qtyAbove,
        UUID transfer,
        UUID product
        ) implements FilterDTO {
}
