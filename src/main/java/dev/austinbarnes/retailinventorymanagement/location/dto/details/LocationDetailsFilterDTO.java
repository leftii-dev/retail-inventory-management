package dev.austinbarnes.retailinventorymanagement.location.dto.details;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LocationDetailsFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 2, max = 100, message = "Address search must be between 2 and 100 characters long")
        String addressContains,
        @Size(min = 3, max = 10, message = "Phone search must be between 3 and 10 characters long")
        String phoneContains,
        @Size(min = 3, max = 10, message = "Email search must be between 3 and 10 characters long")
        String emailContains,
        @Size(min = 3, max = 50, message = "Notes search must be between 3 and 50 characters long")
        String notesContains,
        UUID location,
        UUID manager
) implements FilterDTO {
}
