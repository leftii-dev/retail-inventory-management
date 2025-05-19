package dev.austinbarnes.retailinventorymanagement.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * EmployeeLoginRequestDto represents the data transfer object for employee login requests.
 * It contains the employee code and password fields with validation constraints.
 */
public record EmployeeLoginRequestDto(
        @Size(min = 6, max = 6, message = "Employee code should be 6-digits (only include numbers)")
        @NotBlank(message = "Employee code is required")
        String employeeCode,
        @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
        @NotBlank(message = "Password is required")
        String password
) {
}
