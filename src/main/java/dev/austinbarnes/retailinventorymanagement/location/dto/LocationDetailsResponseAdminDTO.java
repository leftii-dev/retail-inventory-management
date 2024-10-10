package dev.austinbarnes.retailinventorymanagement.location.dto;

import java.util.UUID;

public record LocationDetailsResponseAdminDTO(
        UUID id,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String zipCode,
        String phone,
        String fax,
        String email,
        String notes,
        LocationResponseDTO location,
        EmployeeResponseDTO manager,
        boolean deleted
) implements LocationDetailsResponseDTO{
}
