package dev.austinbarnes.retailinventorymanagement.inventory.dto.status;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;

public record StatusFilterDTO(
        BaseFilterDTO baseFilterDTO,
        String search
) implements FilterDTO {
}
