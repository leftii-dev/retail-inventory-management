package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import jakarta.validation.constraints.Size;

public record LocationTypeRequestDTO(
        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters") String name
) {
}
