package dev.austinbarnes.retailinventorymanagement.employee.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * EmployeeHierarchy is an entity class that represents the hierarchy of employees in the system.
 * It contains references to the employee and their manager.
 * <p>
 * The class extends BaseEntity, which provides common fields such as id, createdAt, modifiedAt,
 * createdBy, modifiedBy, and active.
 */
@Entity
@Table(name = "employee_permission")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"employee", "permission"})
public class EmployeePermission extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    private Permission permission;
}
