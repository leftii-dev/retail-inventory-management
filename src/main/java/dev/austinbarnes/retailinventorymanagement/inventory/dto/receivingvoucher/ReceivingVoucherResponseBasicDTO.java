package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

/**
 * ReceivingVoucherResponseBasicDTO is a Data Transfer Object (DTO) used for transferring basic receiving voucher data.
 * <p>
 * It contains fields for receiving voucher ID, receiving voucher code, notes, purchase order details,
 * location details, vendor details, and status details.
 * <p>
 * This DTO is used to represent the receiving voucher data in a simplified format.
 */
public record ReceivingVoucherResponseBasicDTO(
        UUID id,
        String receivingVoucherCode,
        String notes,
        PurchaseOrderResponseDTO purchaseOrder,
        LocationResponseDTO location,
        VendorResponseDTO vendor,
        StatusResponseDTO status
) implements ReceivingVoucherResponseDTO{
}
