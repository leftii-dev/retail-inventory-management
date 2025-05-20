package dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * InventoryQtyChangeRequestDTO is a Data Transfer Object (DTO) used for transferring inventory quantity change requests.
 * <p>
 * It contains fields for the inventory ID and the quantity change.
 * <p>
 * The class uses validation annotations to ensure that the inventory ID is not null.
 */
public record InventoryQtyChangeRequestDTO(
        @NotNull UUID id,
        int quantityChange) {
}
