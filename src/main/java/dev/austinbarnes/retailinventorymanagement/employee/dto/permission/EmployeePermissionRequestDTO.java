package dev.austinbarnes.retailinventorymanagement.employee.dto.permission;

import java.util.UUID;

public record EmployeePermissionRequestDTO(
        UUID employeeID,
        UUID roleID
) {
}
