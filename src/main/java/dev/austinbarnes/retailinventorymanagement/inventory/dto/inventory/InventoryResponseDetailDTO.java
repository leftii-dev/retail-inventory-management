package dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record InventoryResponseDetailDTO(
        UUID id,
        int quantity,
        ProductResponseDTO product,
        LocationResponseDTO location,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements InventoryResponseDTO{
}
