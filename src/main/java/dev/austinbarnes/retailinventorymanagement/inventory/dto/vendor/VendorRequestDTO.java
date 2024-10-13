package dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VendorRequestDTO(
        @Size(min = 2, max = 100, message = "Vendor name must be between 2 and 100 characters")
        String name,
        @Size(min = 3, max = 60, message = "Address line 1 must be between 3 and 60 characters")
        String addressLine1,
        @Size(min = 3, max = 60, message = "Address line 2 must be between 3 and 60 characters")
        String addressLine2,
        @Size(min = 2, max = 60, message = "Contact name must be between 2 and 60 characters")
        String contactName,
        @Pattern(regexp = "//d{10}", message = "Phone must be 10 digits, no spaces or hyphens")
        String phone,
        @Email(message = "Invalid email format")
        @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
        String email
) {
}
