package dev.austinbarnes.retailinventorymanagement.location.dto;

import java.util.UUID;

public record LocationTypeResponseClientDTO(
        UUID id,
        String name
) implements LocationTypeResponseDTO{
}
