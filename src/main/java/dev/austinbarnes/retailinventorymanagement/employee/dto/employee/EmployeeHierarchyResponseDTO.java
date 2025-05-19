package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.util.UUID;

/**
 * EmployeeHierarchyRequestDTO is a Data Transfer Object (DTO) used for creating or updating
 * employee hierarchy relationships.
 * <p>
 * It contains the IDs of the employee and their manager.
 */
public interface EmployeeHierarchyResponseDTO {
    UUID id();
    EmployeeResponseDTO employee();
    EmployeeResponseDTO manager();
}
