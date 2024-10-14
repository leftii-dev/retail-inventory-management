package dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
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
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) {
}
