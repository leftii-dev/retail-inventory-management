package dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.util.UUID;

public interface InventoryResponseDTO {
    UUID id();
    int quantity();
    ProductResponseDTO product();
    LocationResponseDTO location();
}
