package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;

import java.util.UUID;

public record EmployeePermissionFilterDTO(
        BaseFilterDTO baseFilterDTO,
        UUID employeeId,
        UUID permissionId
) implements FilterDTO {
}
