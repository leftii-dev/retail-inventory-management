package dev.austinbarnes.retailinventorymanagement.employee.dto.role;

import java.util.UUID;

public record EmployeeRoleRequestDTO(
        UUID employeeID,
        UUID roleID
) {
}
