package dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * InventoryResponseDetailDTO is a Data Transfer Object (DTO) used for transferring detailed inventory data.
 * <p>
 * It contains fields for inventory ID, quantity, product details, location details, timestamps for creation and modification,
 * and user IDs for the creator and modifier.
 * <p>
 * This DTO is used to represent the inventory data in a detailed format.
 */
public record InventoryResponseDetailDTO(
        UUID id,
        int quantity,
        ProductResponseDTO product,
        LocationResponseDTO location,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements InventoryResponseDTO{
}
