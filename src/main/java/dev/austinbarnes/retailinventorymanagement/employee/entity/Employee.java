package dev.austinbarnes.retailinventorymanagement.employee.entity;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
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

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamptz")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "modified_at", nullable = false, updatable = false, columnDefinition = "timestamptz")
    @UpdateTimestamp
    private Instant modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false, referencedColumnName = "id")
    Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by", nullable = false, updatable = false, referencedColumnName = "id")
    Employee modifiedBy;

    @Column(name = "is_active", nullable = false)
    @NotNull
    private boolean isActive = true;

    @Column(name = "deleted", nullable = false)
    @NotNull
    private boolean deleted = false;

    @PrePersist
    private void onCreate(){
        this.createdAt = Instant.now();
        this.modifiedAt = Instant.now();
    }

    @PreUpdate
    private void onUpdate(){
        this.modifiedAt = Instant.now();
    }
}
