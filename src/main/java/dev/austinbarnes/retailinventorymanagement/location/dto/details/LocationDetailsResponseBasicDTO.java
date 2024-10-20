package dev.austinbarnes.retailinventorymanagement.location.dto.details;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

public record LocationDetailsResponseBasicDTO(
    UUID id,
    String addressLine1,
    String addressLine2,
    String city,
    String state,
    String zipCode,
    String phone,
    String fax,
    String email,
    EmployeeResponseDTO manager,
    LocationResponseDTO location
) implements LocationDetailsResponseDTO{
}
