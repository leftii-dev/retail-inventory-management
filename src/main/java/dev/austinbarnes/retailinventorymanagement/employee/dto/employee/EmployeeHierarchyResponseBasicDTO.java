package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.util.UUID;

public record EmployeeHierarchyResponseBasicDTO(
        UUID id,
        EmployeeResponseDTO employee,
        EmployeeResponseDTO manager
) implements EmployeeHierarchyResponseDTO {
}
