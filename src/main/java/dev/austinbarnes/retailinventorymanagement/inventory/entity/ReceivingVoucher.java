package dev.austinbarnes.retailinventorymanagement.inventory.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ReceivingVoucher is an entity class representing a receiving voucher in a retail management system.
 * <p>
 * It contains fields for receiving voucher code, freight cost, fee cost, total cost, discount details,
 * payment dates, notes, and references to purchase order, location, vendor, and status.
 * <p>
 * The class uses JPA annotations to map the entity to a database table and includes validation annotations
 * to ensure that the fields are within specified ranges and constraints.
 */
@Entity
@Table(name = "receiving_voucher")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReceivingVoucher extends BaseEntity {
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
    @DecimalMax(value = "100.00", message = "Discount percentage cannot exceed 100.00")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal discountPercent;

    @Column(name = "payment_discount_date")
    @FutureOrPresent(message = "Payment discount date cannot be in the past")
    private LocalDate paymentDiscountDate;

    @Column(name = "payment_net_date")
    @FutureOrPresent(message = "Payment net date cannot be in the past")
    private LocalDate paymentNetDate;

    @Column(name = "notes")
    @Size(max = 3000, message = "Notes cannot exceed 3000 characters")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", referencedColumnName = "id")
    @Valid
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @Valid
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    @Valid
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @Valid
    private Status status;
}
