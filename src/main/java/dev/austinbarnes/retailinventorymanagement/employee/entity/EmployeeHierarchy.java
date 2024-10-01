package dev.austinbarnes.retailinventorymanagement.employee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "employee_hierarchy")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeHierarchy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @NotNull
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    @NotNull
    private Employee manager;

    @Column(name = "deleted", nullable = false)
    @NotNull
    private boolean deleted = false;
}
