package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import java.util.UUID;

public record LocationTypeResponseBasicDTO(
        UUID id,
        String name
) implements LocationTypeResponseDTO{
}
