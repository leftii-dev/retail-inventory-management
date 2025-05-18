package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.time.LocalDate;
import java.util.UUID;

/**
 * EmployeeResponseBasicDTO is a Data Transfer Object (DTO) used for representing
 * basic information about an employee.
 * <p>
 * It contains the employee's ID, name, contact information, date of birth, and employee code.
 */
public interface EmployeeResponseDTO {
    UUID id();
    String nameFirst();
    String nameLast();
    String email();
    String phone();
    LocalDate dateOfBirth();
    String employeeCode();
}
