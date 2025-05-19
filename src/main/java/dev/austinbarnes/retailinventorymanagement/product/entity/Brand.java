package dev.austinbarnes.retailinventorymanagement.product.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Brand is an entity class that represents a brand in the retail inventory management system.
 * It includes fields for the unique identifier, name, description, and timestamps for creation and update.
 * This class is used to map to the "brand" table in the database.
 */
@Entity
@Table(name = "brand")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Brand extends BaseEntity {
    @Column(name = "name", unique = true, nullable = false)
    @Size(min = 1, max = 50, message = "Brand name must be between 1 and 50 characters")
    @NotNull
    private String name;

    @Column(name = "description")
    @Size(max = 3000, message = "Description cannot exceed 3000 characters")
    private String description;
}
