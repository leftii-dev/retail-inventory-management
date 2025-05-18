package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * TransferItemRequestDTO is a Data Transfer Object (DTO) used for transferring transfer item data.
 * <p>
 * It contains fields for cost, quantity, transfer ID, and product ID.
 * <p>
 * The class uses validation annotations to ensure that the cost is within a specified range
 * and that the quantity is within a specified range.
 */
public record TransferItemRequestDTO(
        @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Cost exceeds limit ($9999999999.99), double check costs")
        @NotNull
        BigDecimal cost,
        @Min(value = 1, message = "Minimum transfer quantity is one (1)")
        @Max(value = 10000, message = "Maximum transfer wuantity per line is 10,000")
        @NotNull
        short quantity,
        UUID transferID,
        UUID productID
) {
}
