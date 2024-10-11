package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record PurchaseOrderResponseDetailDTO(
        UUID id,
        String purchaseOrderCode,
        LocalDate dateExpected,
        BigDecimal totalCost,
        Instant createdAt,
        Instant modifiedAt,
        String notes,
        VendorResponseDTO vendor,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        StatusResponseDTO status,
        boolean deleted
) implements PurchaseOrderResponseDTO{
}
