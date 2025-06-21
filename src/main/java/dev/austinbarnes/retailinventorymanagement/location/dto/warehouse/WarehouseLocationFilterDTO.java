package dev.austinbarnes.retailinventorymanagement.location.dto.warehouse;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record WarehouseLocationFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 2, max = 10, message = "Warehouse code search string must be between 2 and 10 characters")
        String codeContains,
        UUID location

) implements FilterDTO {
}
