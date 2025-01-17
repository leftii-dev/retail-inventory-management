package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.time.Instant;
import java.util.UUID;

public record EmployeeHierarchyResponseDetailDTO(
        UUID id,
        EmployeeResponseDTO employee,
        EmployeeResponseDTO manager,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean deleted
) implements EmployeeHierarchyResponseDTO{
}
