package dev.austinbarnes.retailinventorymanagement.inventory.entity;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "purchase_order_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cost_unit", precision = 12, scale = 2)
    @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
    @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99, double check costs")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal costUnit;

    @Column(name = "cost_line_total", precision = 12, scale = 2)
    @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
    @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99, double check costs")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal costLineTotal;

    @Column(name = "quantity")
    @Min(1)
    @Max(10_000)
    private short quantity;

    @Column(name = "created_at", columnDefinition = "timestamptz", updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "modified_at", columnDefinition = "timestamptz")
    @UpdateTimestamp
    private Instant modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", referencedColumnName = "id")
    @Valid
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Valid
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", referencedColumnName = "id", updatable = false)
    @Valid
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id", referencedColumnName = "id")
    @Valid
    private Employee modifiedBy;

    @Column(name = "deleted")
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
