package dev.austinbarnes.retailinventorymanagement.product.dto.category;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;

import java.util.UUID;

public record CategoryHierarchyFilterDTO(
        BaseFilterDTO baseFilterDTO,
        UUID category,
        UUID parentCategory
) implements FilterDTO {
}
