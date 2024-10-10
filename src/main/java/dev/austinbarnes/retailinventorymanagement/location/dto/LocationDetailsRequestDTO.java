package dev.austinbarnes.retailinventorymanagement.location.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LocationDetailsRequestDTO(
        @Size(max = 60, message = "Address Line must be 60 characters or less") String addressLine1,
        @Size(max = 60, message = "Address Line must be 60 characters or less") String addressLine2,
        @Size(max = 30, message = "City must be 30 characters or less") String city,
        @Size(min = 2, max = 2, message = "State must be 2 character abbreviation") String state,
        @Pattern(regexp = "\\d{5}", message = "Zip Code must be exactly 5-digits long") String zipCode,
        @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits, no spaces or hyphens") String phone,
        @Pattern(regexp = "\\d{10}", message = "Fax must be 10 digits, no spaces or hyphens") String fax,
        @Size(min = 5, max = 120, message = "Email must be valid and 120 characters or less") @Email(message = "Invalid email format") String email,
        @Size(max = 3000) String notes,
        UUID locationID,
        UUID managerID
) {
}
