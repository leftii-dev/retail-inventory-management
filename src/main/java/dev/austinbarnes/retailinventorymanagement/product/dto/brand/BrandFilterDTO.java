package dev.austinbarnes.retailinventorymanagement.product.dto.brand;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

public record BrandFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 2, max = 50, message = "Brand search string must be between 2 and 50 characters")
        String query
) implements FilterDTO {
}
