package dev.austinbarnes.retailinventorymanagement.category.dto;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CategoryRequestDTO(
        @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
        String name,
        @Size(max = 1500, message = "Category description cannot exceed 1500 characters")
        String description,
        UUID discountID
) {
}
