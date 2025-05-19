package dev.austinbarnes.retailinventorymanagement.inventory.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * PurchaseOrder is an entity class representing a purchase order in a retail management system.
 * <p>
 * It contains fields for purchase order code, expected date, total cost, notes, vendor details, and status details.
 * <p>
 * The class uses JPA annotations to map the entity to a database table and includes validation annotations
 * to ensure that the purchase order code is of the correct length and that the total cost is within a specified range.
 */
@Entity
@Table(name = "purchase_order")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"status", "vendor"})
@Getter
@Setter
public class PurchaseOrder  extends BaseEntity {
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

    @Column(name = "notes")
    @Size(max = 3000, message = "Notes cannot exceed 3000 characters")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    @Valid
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @Valid
    private Status status;
}
