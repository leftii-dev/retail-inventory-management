package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.util.UUID;

public record EmployeeHierarchyResponseDetailDTO(
        UUID id,
        EmployeeResponseDTO employee,
        EmployeeResponseDTO manager,
        boolean deleted
) implements EmployeeHierarchyResponseDTO{
}
