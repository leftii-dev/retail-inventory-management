package dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor;

import java.util.UUID;

/**
 * VendorResponseBasicDTO is a Data Transfer Object (DTO) used for transferring basic vendor data.
 * <p>
 * It contains fields for vendor ID, vendor code, name, address, contact information, and email.
 * <p>
 * This DTO is used to represent the vendor data in a simplified format.
 */
public interface VendorResponseDTO {
    UUID id();
    String vendorCode();
    String name();
    String addressLine1();
    String addressLine2();
    String city();
    String state();
    String zipCode();
    String contactName();
    String phone();
    String email();
}
