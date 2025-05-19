package dev.austinbarnes.retailinventorymanagement.product.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Discount is an entity class that represents a discount in the retail inventory management system.
 * It includes fields for the unique identifier, discount code, name, description, and discount percentage.
 * This class is used to map to the "discount" table in the database.
 */
@Entity
@Table(name = "discount")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Discount extends BaseEntity {
    @Column(name = "discount_code", unique = true, updatable = false)
    @Size(min = 6, max = 30, message = "Discount code must be between 6 and 30 characters")
    @NotNull
    private String discountCode;

    @Column(name = "name")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    @NotNull
    private String name;

    @Column(name = "description")
    @Size(max = 3000, message = "Description cannot exceed 3000 characters")
    private String description;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    @DecimalMin(value = "0.00", message = "Discount percentage cannot be negative")
    @DecimalMax(value = "100.00", message = "Discount percentage cannot exceed 100%")
    @Digits(integer = 3, fraction = 2)
    @NotNull
    private BigDecimal discountPercentage;
}
