package dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor;

import java.time.Instant;
import java.util.UUID;

public record VendorResponseDetailDTO(
        UUID id,
        String vendorCode,
        String name,
        String addressLine1,
        String addressLine2,
        String contactName,
        String phone,
        String email,
        boolean isActive,
        Instant createdAtID,
        Instant modifiedAtID,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements VendorResponseDTO{
}
