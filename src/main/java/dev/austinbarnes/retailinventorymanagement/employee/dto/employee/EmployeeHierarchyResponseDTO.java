package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.util.UUID;

public interface EmployeeHierarchyResponseDTO {
    UUID id();
    EmployeeResponseDTO employee();
    EmployeeResponseDTO manager();
}
