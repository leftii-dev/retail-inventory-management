package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransferItemResponseDetailDTO(
        UUID id,
        BigDecimal cost,
        short quantity,
        Instant createdAt,
        Instant modifiedAt,
        TransferResponseDTO transferR,
        ProductResponseDTO product,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) {
}
