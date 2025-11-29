package dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor;

import java.time.Instant;
import java.util.UUID;

/**
 * VendorResponseDetailDTO is a Data Transfer Object (DTO) used for transferring detailed vendor data.
 * <p>
 * It contains fields for vendor ID, vendor code, name, address, contact information, email, and status.
 * <p>
 * This DTO is used to represent the vendor data in a detailed format.
 */
public record VendorResponseDetailDTO(
        UUID id,
        String vendorCode,
        String name,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String zipCode,
        String contactName,
        String phone,
        String email,
        boolean isActive,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        UUID modifiedByID,
        boolean active
) implements VendorResponseDTO{
}
