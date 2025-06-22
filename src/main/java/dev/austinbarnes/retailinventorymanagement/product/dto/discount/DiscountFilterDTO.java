package dev.austinbarnes.retailinventorymanagement.product.dto.discount;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

public record DiscountFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 2, max = 10, message = "Discount code search string must be between 2 and 10 characters")
        String codeContains,
        @Size(min = 2, max = 50, message = "Search string must be between 2 and 50 characters")
        String query
) implements FilterDTO {
}
