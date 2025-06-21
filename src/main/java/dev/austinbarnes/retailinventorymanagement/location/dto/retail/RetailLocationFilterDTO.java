package dev.austinbarnes.retailinventorymanagement.location.dto.retail;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RetailLocationFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 2, max = 10, message = "Retail location code search string must be between 2 and 10 characters long")
        String codeContains,
        UUID location
) implements FilterDTO {
}
