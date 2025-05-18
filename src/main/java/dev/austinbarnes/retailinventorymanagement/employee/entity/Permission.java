package dev.austinbarnes.retailinventorymanagement.employee.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * Permission is an entity class that represents a permission in the system.
 * It contains fields for the name and description of the permission.
 * <p>
 * The class extends BaseEntity, which provides common fields such as id, createdAt, modifiedAt,
 * createdBy, modifiedBy, and active.
 */
@Entity
@Table(name = "permission")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"description"})
public class Permission extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    @NotNull
    @Size(min = 3, max = 60, message = "Name of permission must be 3 to 60 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 3000, message = "Description cannot exceed 3000 characters")
    private String description;
}
