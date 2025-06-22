package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CategoryFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 2, max = 10, message = "Category code search string must be between 2 and 10 characters")
        String codeContains,
        @Size(min = 2, max = 50, message = "Category search query must be between 2 and 50 characters")
        String query,
        UUID discount
) implements FilterDTO {
}
