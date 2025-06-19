package dev.austinbarnes.retailinventorymanagement.location.dto;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LocationFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 2, max = 100, message = "Location name must be between 2 and 100 characters long")
        String nameContains,
        UUID locationType
) implements FilterDTO {
}
