package dev.austinbarnes.retailinventorymanagement.location.dto.type;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

public record LocationTypeFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 2, max = 50, message = "Name search must be between 2 and 50 characters")
        String nameContains
) implements FilterDTO {
}
