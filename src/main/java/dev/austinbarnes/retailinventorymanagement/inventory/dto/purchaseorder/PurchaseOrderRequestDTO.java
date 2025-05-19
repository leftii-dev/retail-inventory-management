package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * PurchaseOrderRequestDTO is a Data Transfer Object (DTO) used for creating or updating
 * purchase orders.
 * <p>
 * It contains fields for the expected date, total cost, notes, vendor ID, and status ID.
 */
public record PurchaseOrderRequestDTO(
        @FutureOrPresent LocalDate dateExpected,
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Total cost cannot be over $9999999999.99, double check costs")
        @Digits(integer = 10, fraction = 2) BigDecimal totalCost,
        @Size(max = 3000, message = "Notes cannot exceed 3000 characters") String notes,
        UUID vendorID,
        UUID statusID
) {
}
