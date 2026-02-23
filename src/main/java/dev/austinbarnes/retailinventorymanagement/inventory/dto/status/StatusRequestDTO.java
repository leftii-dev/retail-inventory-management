package dev.austinbarnes.retailinventorymanagement.inventory.dto.status;

import jakarta.validation.constraints.Size;

/**
 * StatusRequestDTO is a Data Transfer Object (DTO) used for transferring status data.
 * <p>
 * It contains fields for status name and description.
 * <p>
 * The class uses validation annotations to ensure that the name is between 2 and 50 characters
 * and that the description does not exceed 3000 characters.
 */
public record StatusRequestDTO(
        @Size(min = 2, max = 50, message = "Status must be between 2 and 50 characters")
        String name,
        @Size(max = 3000, message = "Description cannot exceed 3000 characters")
        String description
) {
}
