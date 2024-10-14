package dev.austinbarnes.retailinventorymanagement.location.dto;

import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDTO;

import java.util.UUID;

public record LocationResponseBasicDTO(
        UUID id,
        String name,
        LocationTypeResponseDTO locationType
) implements LocationResponseDTO{
}
