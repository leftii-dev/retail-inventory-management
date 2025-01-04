package dev.austinbarnes.retailinventorymanagement.employee.entity;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"user", "phone", "email", "dateOfBirth"})
public class Employee extends BaseEntity {

    @OneToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

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

    @Column(name = "employee_code", unique = true, updatable = false, nullable = false)
    @Size(min = 10, max = 10, message = "Employee code must be 10 characters")
    @NotNull
    private String employeeCode;

    @Column(name = "is_current_employee", nullable = false)
    @NotNull
    private boolean isCurrentEmployee = true;
}
