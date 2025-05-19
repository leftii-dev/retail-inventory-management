package dev.austinbarnes.retailinventorymanagement.product.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Brand is an entity class that represents a brand in the retail inventory management system.
 * It includes fields for the unique identifier, name, description, and timestamps for creation and update.
 * This class is used to map to the "brand" table in the database.
 */
@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = "discount")
public class Category extends BaseEntity {
    @Column(name = "category_code", nullable = false, updatable = false, unique = true)
    @NotNull
    private String categoryCode;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 3000, message = "Category description cannot exceed 3000 characters")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    @Valid
    private Discount discount;
}
