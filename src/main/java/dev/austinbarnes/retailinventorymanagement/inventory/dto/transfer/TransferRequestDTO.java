package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransferRequestDTO (
        LocalDate date,
        @DecimalMax(value = "9999999999.99", message = "Total cost exceeds limit, double check entered costs")
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative, double check entered costs")
        BigDecimal totalCost,
        @Min(value = 1, message = "Total quantity cannot be less that one (1), double check entered quantities")
        @Max(value = 10000, message = "Total quantity exceeds limit, double check entered quantities")
        int totalQuantity,
        UUID locationTo,
        UUID locationFrom
){}
