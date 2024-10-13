package dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor;

import java.util.UUID;

public record VendorResponseBasicDTO(
        UUID id,
        String vendorCode,
        String name,
        String addressLine1,
        String addressLine2,
        String contactName,
        String phone,
        String email
) implements VendorResponseDTO{
}
