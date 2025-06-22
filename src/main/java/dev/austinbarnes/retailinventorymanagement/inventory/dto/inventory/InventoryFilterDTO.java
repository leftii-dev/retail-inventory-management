package dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;

import java.util.UUID;

public record InventoryFilterDTO(
        BaseFilterDTO baseFilterDTO,
        Integer qtyLessThan,
        Integer qtyGreaterThan,
        UUID product,
        UUID location
) implements FilterDTO {
}
