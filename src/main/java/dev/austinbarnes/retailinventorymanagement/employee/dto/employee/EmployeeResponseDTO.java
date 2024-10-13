package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.time.LocalDate;
import java.util.UUID;

public interface EmployeeResponseDTO {
    UUID id();
    String nameFirst();
    String nameLast();
    String email();
    String phone();
    LocalDate dateOfBirth();
    String employeeCode();
}
