package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * EmployeeResponseDetailDTO is a Data Transfer Object (DTO) used for representing
 * detailed information about an employee.
 * <p>
 * It contains the employee's ID, name, contact information, date of birth, employee code,
 * creation and modification timestamps, and status information.
 *
 * @param id           The ID of the employee.
 * @param nameFirst    The first name of the employee.
 * @param nameLast     The last name of the employee.
 * @param phone        The phone number of the employee.
 * @param email        The email address of the employee.
 * @param dateOfBirth  The date of birth of the employee.
 * @param employeeCode The unique code assigned to the employee.
 * @param createdAt    The timestamp when the employee record was created.
 * @param modifiedAt   The timestamp when the employee record was last modified.
 * @param createdBy    The ID of the user who created the employee record.
 * @param modifiedBy   The ID of the user who last modified the employee record.
 * @param isCurrentEmployee Indicates if the employee is currently active.
 * @param active       Indicates if the employee record is active or not.
 */
public record EmployeeResponseDetailDTO(
        UUID id,
        String nameFirst,
        String nameLast,
        String phone,
        String email,
        LocalDate dateOfBirth,
        String employeeCode,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        boolean isCurrentEmployee,
        boolean active
) implements EmployeeResponseDTO{
}
