package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.time.Instant;
import java.util.UUID;

/**
 * EmployeeHierarchyResponseDetailDTO is a Data Transfer Object (DTO) used for representing
 * detailed information about an employee's hierarchy relationship.
 * <p>
 * It contains the IDs of the employee and their manager, along with additional metadata.
 *
 * @param id        The ID of the employee hierarchy relationship.
 * @param employee  The employee involved in the hierarchy.
 * @param manager   The manager of the employee.
 * @param createdAt The timestamp when the relationship was created.
 * @param modifiedAt The timestamp when the relationship was last modified.
 * @param createdBy The ID of the user who created the relationship.
 * @param modifiedBy The ID of the user who last modified the relationship.
 * @param deleted   Indicates whether the relationship has been deleted.
 */
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
