package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.util.UUID;

/**
 * EmployeeHierarchyRequestDTO is a Data Transfer Object (DTO) used for creating or updating
 * employee hierarchy relationships.
 * <p>
 * It contains the IDs of the employee and their manager.
 *
 * @param employeeID The ID of the employee.
 * @param managerID  The ID of the manager.
 */
public record EmployeeHierarchyRequestDTO(
        UUID employeeID,
        UUID managerID
) {
}
