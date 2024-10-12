package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ReceivingVoucherResponseDetailDTO(
        UUID id,
        String receivingVoucherCode,
        BigDecimal freightCost,
        BigDecimal feeCost,
        BigDecimal totalCost,
        BigDecimal discountTotal,
        BigDecimal discountPercent,
        LocalDate paymentDiscountDate,
        LocalDate paymentNetDate,
        Instant createdAt,
        Instant modifiedAt,
        String notes,
        PurchaseOrderResponseDTO purchaseOrder,
        LocationResponseDTO location,
        VendorResponseDTO vendor,
        StatusResponseDTO status,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) implements ReceivingVoucherResponseDTO{
}