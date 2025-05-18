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

/**
 * ReceivingVoucherItem is an entity class representing an item in a receiving voucher in a retail management system.
 * <p>
 * It contains fields for quantity, discount percentage, discount reason, cost per unit, total cost for the line item,
 * and references to the product and receiving voucher associated with the item.
 * <p>
 * The class uses JPA annotations to map the entity to a database table and includes validation annotations
 * to ensure that the cost and quantity are within specified ranges.
 */
@Entity
@Table(name = "transfer_item")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = {"transfer", "product"}, callSuper = true)
public class TransferItem extends BaseEntity {
    @Column(name = "cost", nullable = false)
    @DecimalMin(value = "0.00", message = "Cost cannot be negative, double check costs")
    @DecimalMax(value = "9999999999.99", message = "Cost exceeds limit ($9999999999.99), double check costs")
    @NotNull
    private BigDecimal cost;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Minimum transfer quantity is one (1)")
    @Max(value = 10000, message = "Maximum transfer wuantity per line is 10,000")
    @NotNull
    private short quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_id", referencedColumnName = "id")
    @Valid
    private Transfer transfer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Valid
    private Product product;
}
