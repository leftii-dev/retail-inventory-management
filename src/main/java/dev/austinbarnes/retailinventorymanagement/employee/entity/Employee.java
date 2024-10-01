package dev.austinbarnes.retailinventorymanagement.employee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name_first", nullable = false)
    @NotNull
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    private String nameFirst;

    @Column(name = "name_last", nullable = false)
    @NotNull
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    private String nameLast;

    @Column(name = "phone")
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits, no spaces or hyphens")
    private String phone;

    @Column(name = "email", nullable = false)
    @NotNull
    @Size(min = 5, max = 100, message = "Email address must be between 5 and 100 characters")
    @Email(message = "Invalid email address format")
    private String email;

    @Column(name = "date_of_birth")
    @Past
    private LocalDate dateOfBirth;

    //TODO: Add genereator
    @Column(name = "employee_code", unique = true)
    @Size(min = 10, max = 10)
    private String employeeCode;

    @Column(name = "is_active", nullable = false)
    @NotNull
    private boolean isActive = true;

    @Column(name = "deleted", nullable = false)
    @NotNull
    private boolean deleted = false;
}
