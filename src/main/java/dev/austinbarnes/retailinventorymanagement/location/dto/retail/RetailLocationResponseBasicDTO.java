package dev.austinbarnes.retailinventorymanagement.location.dto.retail;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

public record RetailLocationResponseBasicDTO(
        UUID id,
        LocationResponseDTO location
) implements RetailLocationResponseDTO{
}
