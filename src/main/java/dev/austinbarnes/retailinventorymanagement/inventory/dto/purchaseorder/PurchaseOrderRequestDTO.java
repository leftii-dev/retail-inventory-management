package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PurchaseOrderRequestDTO(
        @FutureOrPresent LocalDate dateExpected,
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Total cost cannot be over $9999999999.99, double check costs")
        @Digits(integer = 10, fraction = 2) BigDecimal totalCost,
        @Size(max = 3000, message = "Notes cannot exceed 3000 characters") String notes,
        @Valid VendorResponseDTO vendor,
        @Valid StatusResponseDTO status
) {
}
