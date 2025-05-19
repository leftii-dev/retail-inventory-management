package dev.austinbarnes.retailinventorymanagement.auth.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Role represents a user role in the system.
 * It contains the role name and inherits from BaseEntity.
 * The role name must be unique and cannot be null or empty.
 */
@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Role extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @NotNull(message = "Role name cannot be null")
    @NotEmpty(message = "Role name cannot be empty")
    private String name;
}
