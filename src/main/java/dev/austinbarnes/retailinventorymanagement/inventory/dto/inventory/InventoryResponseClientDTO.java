package dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.util.UUID;

public record InventoryResponseClientDTO(
        UUID id,
        int quantity,
        ProductResponseDTO product,
        LocationResponseDTO location
) implements InventoryResponseDTO {
}
