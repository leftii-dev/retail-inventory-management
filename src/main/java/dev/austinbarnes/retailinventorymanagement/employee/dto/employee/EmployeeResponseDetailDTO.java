package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

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
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean isActive,
        boolean deleted
) implements EmployeeResponseDTO{
}
