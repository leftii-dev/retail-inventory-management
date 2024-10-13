package dev.austinbarnes.retailinventorymanagement.inventory.dto.status;

import java.util.UUID;

public record StatusResponseBasicDTO(
        UUID id,
        String name,
        String description
) implements StatusResponseDTO{
}
