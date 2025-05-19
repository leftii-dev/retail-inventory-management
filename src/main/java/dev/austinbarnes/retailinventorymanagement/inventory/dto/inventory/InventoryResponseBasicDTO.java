package dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.util.UUID;

/**
 * InventoryResponseBasicDTO is a Data Transfer Object (DTO) used for transferring basic inventory data.
 * <p>
 * It contains fields for inventory ID, quantity, product details, and location details.
 * <p>
 * This DTO is used to represent the inventory data in a simplified format.
 */
public record InventoryResponseBasicDTO(
        UUID id,
        int quantity,
        ProductResponseDTO product,
        LocationResponseDTO location
) implements InventoryResponseDTO {
}
