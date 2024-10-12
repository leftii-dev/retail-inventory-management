package dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

public interface ReceivingVoucherResponseDTO {
    UUID id();
    String receivingVoucherCode();
    String notes();
    PurchaseOrderResponseDTO purchaseOrder();
    LocationResponseDTO location();
    VendorResponseDTO vendor();
    StatusResponseDTO status();
}