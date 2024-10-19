package dev.austinbarnes.retailinventorymanagement.inventory.entity;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
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
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "purchase_order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "purchase_order_code", nullable = false, unique = true, updatable = false)
    @Size(min = 10, max = 10, message = "Purchase order code must be 10 characters")
    @NotNull
    private String purchaseOrderCode;

    @Column(name = "date_expected")
    @FutureOrPresent
    private LocalDate dateExpected;

    @Column(name = "total_cost", precision = 12, scale = 2)
    @DecimalMin(value = "0.00", message = "Total cost cannot be negative, double check costs")
    @DecimalMax(value = "9999999999.99", message = "Total cost cannot be over $9999999999.99, double check costs")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal totalCost;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private Instant modifiedAt;

    @Column(name = "notes")
    @Size(max = 3000, message = "Notes cannot exceed 3000 characters")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    @Valid
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    @Valid
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_ny_id", referencedColumnName = "id")
    @Valid
    private Employee modifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @Valid
    private Status status;

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
