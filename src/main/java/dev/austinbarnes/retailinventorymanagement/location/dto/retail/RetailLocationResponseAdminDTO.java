package dev.austinbarnes.retailinventorymanagement.location.dto.retail;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.util.UUID;

public record RetailLocationResponseAdminDTO(
        UUID id,
        String retailLocationCode,
        LocationResponseDTO location,
        boolean deleted
) implements RetailLocationResponseDTO{
}
