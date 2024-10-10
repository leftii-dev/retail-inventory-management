package dev.austinbarnes.retailinventorymanagement.location.dto;

import java.util.UUID;

public record LocationTypeResponseAdminDTO(
        UUID id,
        String name,
        boolean deleted
) implements LocationTypeResponseDTO{
}
