package dev.austinbarnes.retailinventorymanagement.inventory.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "receiving_voucher_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"receivingVoucher", "product"}, callSuper = true)
public class ReceivingVoucherItem extends BaseEntity {
    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be a positive number")
    @Max(value = 10_000, message = "Quantity cannot exceed 10,000, if you need more, add a separate line item for the excess")
    @NotNull
    private short quantity;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    @DecimalMax(value = "100.00", message = "Discount percentage cannot exceed 100.00%")
    @DecimalMin(value = "0.00", message = "Discount percentage cannot be negative")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal discountPercentage;

    @Column(name = "discount_reason")
    @Size(max = 50, message = "Discount reason cannot exceed 50 characters")
    private String discountReason;

    @Column(name = "cost_unit", precision = 12, scale = 2)
    @DecimalMin(value = "0.00", message = "Unit cost cannot be negative")
    @DecimalMax(value = "9999999999.99", message = "Max unit cost annoy exceed $9,999,999,999.99, double check costs")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal costUnit;

    @Column(name = "cost_line_total", precision = 12, scale = 2)
    @DecimalMin(value = "0.00", message = "Line cost total cannot be negative")
    @DecimalMax(value = "9999999999.99", message = "Max line cost total cannot exceed $9,999,999,999.99, double check costs")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal costLineTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Valid
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiving_voucher_id", referencedColumnName = "id")
    @Valid
    private ReceivingVoucher receivingVoucher;
}
