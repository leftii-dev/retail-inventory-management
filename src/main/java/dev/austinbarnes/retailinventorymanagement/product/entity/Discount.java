package dev.austinbarnes.retailinventorymanagement.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "discount")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @Column(name = "is_active")
    @NotNull
    private boolean active;

    @Column(name = "deleted")
    @NotNull
    private boolean deleted;
}
