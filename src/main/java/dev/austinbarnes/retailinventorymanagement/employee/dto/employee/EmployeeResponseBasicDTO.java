package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.time.LocalDate;
import java.util.UUID;

/**
 * EmployeeResponseBasicDTO is a Data Transfer Object (DTO) used for representing
 * basic information about an employee.
 * <p>
 * It contains the employee's ID, name, contact information, date of birth, and employee code.
 *
 * @param id           The ID of the employee.
 * @param nameFirst    The first name of the employee.
 * @param nameLast     The last name of the employee.
 * @param phone        The phone number of the employee.
 * @param email        The email address of the employee.
 * @param dateOfBirth  The date of birth of the employee.
 * @param employeeCode The unique code assigned to the employee.
 */
public record EmployeeResponseBasicDTO(
        UUID id,
        String nameFirst,
        String nameLast,
        String phone,
        String email,
        LocalDate dateOfBirth,
        String employeeCode
) implements EmployeeResponseDTO{
}
