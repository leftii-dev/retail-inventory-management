package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeRequestDTO(
        @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
        String nameFirst,
        @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
        String nameLast,
        @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits, no spaces or hyphens")
        String phone,
        @Size(min = 5, max = 100, message = "Email address must be between 5 and 100 characters")
        @Email(message = "Invalid email address format")
        String email,
        @Past
        LocalDate dateOfBirth,
        UUID userId
) {
}
