package dev.austinbarnes.retailinventorymanagement.location.dto.details;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

public interface LocationDetailsResponseDTO {
    UUID id();

    String addressLine1();

    String addressLine2();

    String city();

    String state();

    String zipCode();

    String phone();

    String fax();

    String email();

    LocationResponseDTO location();
}