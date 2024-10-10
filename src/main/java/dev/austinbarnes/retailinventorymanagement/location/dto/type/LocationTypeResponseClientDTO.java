package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import java.util.UUID;

public record LocationTypeResponseClientDTO(
        UUID id,
        String name
) implements LocationTypeResponseDTO{
}
