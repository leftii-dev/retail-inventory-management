package dev.austinbarnes.retailinventorymanagement.inventory.entity;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "receiving_voucher")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReceivingVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "receiving_voucher_code", updatable = false, nullable = false, unique = true)
    @Size(min = 10, max = 10, message = "Receiving voucher code must be 10 characters")
    @NotNull
    private String receivingVoucherCode;

    @Column(name = "cost_freight", precision = 12, scale = 2)
    @DecimalMin(value = "0.00", message = "Freight cost cannot be negative")
    @DecimalMax(value = "9999999999.99", message = "Freight cost cannot exceed $9,999,999,999.99")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal freightCost;

    @Column(name = "cost_fee", precision = 12, scale = 2)
    @DecimalMin(value = "0.00", message = "Fee cost cannot be negative")
    @DecimalMax(value = "9999999999.99", message = "Fee cost cannot exceed $9,999,999,999.99")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal feeCost;

    @Column(name = "cost_total")
    @DecimalMin(value = "0.00", message = "Total cost cannot be negative")
    @DecimalMax(value = "9999999999.99", message = "Total cost cannot exceed $9,999,999,999.99")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal totalCost;

    @Column(name = "discount_total", precision = 12, scale = 2)
    @DecimalMin(value = "0.00", message = "Total discount cannot be negative")
    @DecimalMax(value = "9999999999.99", message = "Discount cannot be greater than total cost")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal discountTotal;

    @Column(name = "payment_discount", precision = 5, scale = 2)
    @DecimalMin(value = "0.00", message = "Discount percentage cannot be negative")
    @DecimalMax(value = "100.00", message = "Discount percantage cannot exceed 100.00")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal discountPercent;

    @Column(name = "payment_discount_date")
    @FutureOrPresent(message = "Payment discount date cannot be in the past")
    private LocalDate paymentDiscountDate;

    @Column(name = "payment_net_date")
    @FutureOrPresent(message = "Payment net date cannot be in the past")
    private LocalDate paymentNetDate;

    @Column(name = "created_at", updatable = false, columnDefinition = "timestamptz")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "modified_at", columnDefinition = "timestamptz")
    @UpdateTimestamp
    private Instant modifiedAt;

    @Column(name = "notes")
    @Size(max = 3000, message = "Notes cannot exceed 3000 characters")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", referencedColumnName = "id")
    @Valid
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @NotNull(message = "Location must be set")
    @Valid
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    @NotNull(message = "Vendor must be set")
    @Valid
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @NotNull(message = "Status must be set")
    @Valid
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", referencedColumnName = "id", updatable = false)
    @NotNull(message = "Created By cannot be null")
    @Valid
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id", referencedColumnName = "id")
    @NotNull(message = "Modified By cannot be null")
    @Valid
    private Employee modifiedBy;

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
