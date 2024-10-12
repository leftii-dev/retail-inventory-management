package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.util.UUID;

public record EmployeeHierarchyRequestDTO(
        UUID employeeID,
        UUID managerID
) {
}
