package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import java.util.UUID;

public record LocationTypeResponseAdminDTO(
        UUID id,
        String name,
        boolean deleted
) implements LocationTypeResponseDTO{
}
