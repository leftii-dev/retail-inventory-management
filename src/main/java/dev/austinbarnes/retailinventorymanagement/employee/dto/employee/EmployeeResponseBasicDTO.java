package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.time.LocalDate;
import java.util.UUID;

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
