package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;

import java.util.UUID;

public record EmployeeHierarchyFilterDTO(
    BaseFilterDTO baseFilterDTO,
    UUID employeeID,
    UUID managerID
) {
}
