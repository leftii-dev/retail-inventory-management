package dev.austinbarnes.retailinventorymanagement.inventory.dto.status;

import jakarta.validation.constraints.Size;

public record StatusRequestDTO(
        @Size(min = 2, max = 50, message = "Status must be between 2 and 50 characters") String name,
        @Size(max = 3000, message = "Description cannot exceed 3000 characters") String description
) {
}
