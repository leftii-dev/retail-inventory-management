package dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * InventoryRequestDTO is a Data Transfer Object (DTO) used for transferring inventory data.
 * <p>
 * It contains fields for quantity, product ID, and location ID.
 * <p>
 * The class uses validation annotations to ensure that the quantity is within a specified range
 * and that the product ID and location ID are not null.
 */
public record InventoryRequestDTO (
    @Min(value = 0, message = "Quantity cannot be negative") @Max(value = 99_999_999, message = "Quantity cannot exceed 99,999,999") @NotNull
    int quantity,
    UUID productID,
    UUID locationID
){
}
