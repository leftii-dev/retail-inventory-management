package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

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
