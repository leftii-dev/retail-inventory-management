package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransferFilterDTO(
        BaseFilterDTO baseFilterDTO,
        LocalDate transferDate,
        LocalDate transferDateBefore,
        LocalDate transferDateAfter,
        @Size(min = 3, max = 10, message = "Transfer code search string must be between 3 and 10 characters")
        String codeContains,
        @DecimalMax(value = "9999999999.99", message = "Total cost exceeds limit, double check entered costs")
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative, double check entered costs")
        BigDecimal totalCost,
        @DecimalMax(value = "9999999999.99", message = "Total cost exceeds limit, double check entered costs")
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative, double check entered costs")
        BigDecimal totalCostBelow,
        @DecimalMax(value = "9999999999.99", message = "Total cost exceeds limit, double check entered costs")
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative, double check entered costs")
        BigDecimal totalCostAbove,
        @Min(value = 1, message = "Total quantity cannot be less that one (1), double check entered quantities")
        @Max(value = 1_000_000, message = "Total quantity exceeds limit, double check entered quantities")
        Integer qty,
        @Min(value = 1, message = "Total quantity cannot be less that one (1), double check entered quantities")
        @Max(value = 1_000_000, message = "Total quantity exceeds limit, double check entered quantities")
        Integer qtyBelow,
        @Min(value = 1, message = "Total quantity cannot be less that one (1), double check entered quantities")
        @Max(value = 1_000_000, message = "Total quantity exceeds limit, double check entered quantities")
        Integer qtyAbove,
        UUID transferTo,
        UUID transferFrom
        ) implements FilterDTO {
}
