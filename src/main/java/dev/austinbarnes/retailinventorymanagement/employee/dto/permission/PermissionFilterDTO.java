package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

public record PermissionFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 3, max = 20, message = "Search string for name must be between 3 and 20 characters")
        String nameContains,
        @Size(min = 3, max = 20, message = "Search string for description must be between 3 and 20 characters")
        String descriptionContains
) implements FilterDTO {}
