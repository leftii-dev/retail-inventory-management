package dev.austinbarnes.retailinventorymanagement.inventory.dto.status;

import java.util.UUID;

/**
 * StatusResponseBasicDTO is a Data Transfer Object (DTO) used for transferring basic status data.
 * <p>
 * It contains fields for status ID, name, and description.
 * <p>
 * This DTO is used to represent the status data in a simplified format.
 */
public interface StatusResponseDTO {
    UUID id();
    String name();
    String description();
}
