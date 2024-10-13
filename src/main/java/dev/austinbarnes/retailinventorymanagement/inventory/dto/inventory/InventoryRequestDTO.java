package dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InventoryRequestDTO (
    @Min(value = 0, message = "Quantity cannot be negative") @Max(value = 99999999, message = "Quantity cannot exceed 99,999,999") @NotNull int quantity,
    UUID productID,
    UUID locationID
){
}
