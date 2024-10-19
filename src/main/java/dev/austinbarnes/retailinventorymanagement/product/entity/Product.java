package dev.austinbarnes.retailinventorymanagement.product.entity;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sku", nullable = false, unique = true)
    @Size(max = 20, message = "Sku cannot exceed 20 characters")
    @NotNull
    private String sku;

    @Column(name = "product_code", nullable = false, updatable = false, unique = true)
    private String productCode;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 3000, message = "Description cannot exceed 3000 characters")
    private String description;

    @Column(name = "cost", precision = 12, scale = 2)
    @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99")
    @DecimalMin(value = "0.01", message = "Cost cannot be less than $0.01")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal cost;

    @Column(name = "price", precision = 12, scale = 2)
    @DecimalMax(value = "9999999999.99", message = "Price cannot exceed $9,999,999,999.99")
    @DecimalMin(value = "0.01", message = "Price cannot be less than $0.01")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @Column(name = "weight", precision = 8, scale = 2)
    @DecimalMax(value = "999999.99", message = "Weight cannot exceed 999,999.99")
    @DecimalMin(value = "0.00", message = "Weight cannot be negative")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal weight;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dimensions", columnDefinition = "jsonb")
    private Map<String, Object> dimensions;

    // For attributes that may not apply to all items (Size, Color, Box Counts, etc.)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_details", columnDefinition = "jsonb")
    private Map<String, Object> additionalDetails;

    @Column(name = "created_at", updatable = false, columnDefinition = "timestamptz")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "modified_at", columnDefinition = "timestamptz")
    @UpdateTimestamp
    private Instant modifiedAt;

    @Column(name = "is_active", nullable = false)
    @NotNull
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @Valid
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", referencedColumnName = "id", updatable = false)
    @Valid
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id", referencedColumnName = "id")
    @Valid
    private Employee modifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @Valid
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    @Valid
    Discount discount;

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
