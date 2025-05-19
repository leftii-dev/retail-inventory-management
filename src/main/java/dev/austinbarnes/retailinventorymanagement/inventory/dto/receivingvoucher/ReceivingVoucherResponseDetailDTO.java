package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * ReceivingVoucherResponseDetailDTO is a Data Transfer Object (DTO) used for transferring detailed receiving voucher data.
 * <p>
 * It contains fields for receiving voucher ID, receiving voucher code, freight cost, fee cost, total cost,
 * discount total, discount percentage, payment discount date, payment net date, creation and modification timestamps,
 * notes, purchase order details, location details, vendor details, status details, creator and modifier IDs,
 * and active status.
 * <p>
 * This DTO is used to represent the receiving voucher data in a detailed format.
 */
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
        UUID createdBy,
        UUID modifiedBy,
        boolean active
) implements ReceivingVoucherResponseDTO{
}
