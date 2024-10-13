package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PurchaseOrderItemResponseDetailDTO(
        UUID id,
        BigDecimal costUnit,
        BigDecimal costLineTotal,
        short quantity,
        Instant createdAt,
        Instant modifiedAt,
        PurchaseOrderResponseDTO purchaseOrder,
        ProductResponseDTO product,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) implements PurchaseOrderItemResponseDTO{
}
