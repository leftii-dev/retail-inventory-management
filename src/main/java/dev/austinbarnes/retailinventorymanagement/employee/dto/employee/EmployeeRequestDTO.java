package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * EmployeeRequestDTO is a Data Transfer Object (DTO) used for creating or updating employee
 * information.
 * <p>
 * It contains the first name, last name, phone number, email address, date of birth, and user ID of
 * the employee.
 *
 * @param nameFirst   The first name of the employee.
 * @param nameLast    The last name of the employee.
 * @param phone       The phone number of the employee.
 * @param email       The email address of the employee.
 * @param dateOfBirth The date of birth of the employee.
 * @param userID      The ID of the user associated with the employee.
 */
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
        UUID userID,
        Set<UUID> permissionIDs
) {
}
