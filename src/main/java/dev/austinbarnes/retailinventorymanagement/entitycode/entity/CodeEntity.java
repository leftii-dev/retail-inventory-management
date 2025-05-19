package dev.austinbarnes.retailinventorymanagement.entitycode.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * CodeEntity is a JPA entity representing a code in the database.
 * <p>
 * It contains fields for the code name and the code itself.
 * <p>
 * The class extends BaseEntity, which provides common fields like ID and timestamps.
 */
@Entity
@Table(name = "code")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class CodeEntity extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "code")
    private int code;
}
