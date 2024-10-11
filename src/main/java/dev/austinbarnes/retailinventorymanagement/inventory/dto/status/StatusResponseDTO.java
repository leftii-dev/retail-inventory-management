package dev.austinbarnes.retailinventorymanagement.inventory.dto.status;

import java.util.UUID;

public interface StatusResponseDTO {
    UUID id();
    String name();
    String description();
}
