package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.time.Instant;
import java.util.UUID;

public record EmployeeHierarchyResponseDetailDTO(
        UUID id,
        EmployeeResponseDTO employee,
        EmployeeResponseDTO manager,
        Instant createdAt,
        Instant modifiedAt,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) implements EmployeeHierarchyResponseDTO{
}
