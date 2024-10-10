package dev.austinbarnes.retailinventorymanagement.location.dto;

import java.util.UUID;

public record LocationResponseClientDTO (
        UUID id,
        String name,
        LocationTypeResponseDTO locationType
) implements LocationResponseDTO{
}
