package dev.austinbarnes.retailinventorymanagement.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Employee is an entity class that represents an employee in the system.
 * It contains fields for the employee's first name, last name, phone number, email address,
 * date of birth, employee code, and whether they are a current employee.
 * <p>
 * The class also includes validation annotations to ensure that the data meets certain criteria.
 * It extends the BaseEntity class, which provides common fields such as id, createdAt, modifiedAt,
 * createdBy, modifiedBy, and active status.
 */
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
    @JsonIgnore
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

    @Column(name = "email", nullable = false, unique = true)
    @NotNull
    @Size(min = 5, max = 100, message = "Email address must be between 5 and 100 characters")
    @Email(message = "Invalid email address format")
    private String email;

    @Column(name = "date_of_birth")
    @Past(message = "Date of birth must be in the past.")
    private LocalDate dateOfBirth;

    @Column(name = "employee_code", unique = true, updatable = false, nullable = false)
    @Size(min = 10, max = 10, message = "Employee code must be 10 characters")
    @NotNull
    private String employeeCode;

    @Column(name = "is_current_employee")
    @NotNull
    private boolean isCurrentEmployee = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_permissions",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();
}
