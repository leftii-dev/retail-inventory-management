package dev.austinbarnes.retailinventorymanagement.location.dto.retail;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

public interface RetailLocationResponseDTO {
    UUID id();
    LocationResponseDTO location();
}
