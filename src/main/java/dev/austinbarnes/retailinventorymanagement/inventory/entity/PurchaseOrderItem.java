package dev.austinbarnes.retailinventorymanagement.inventory.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * PurchaseOrderItem is an entity class representing an item in a purchase order in a retail management system.
 * <p>
 * It contains fields for cost per unit, total cost for the line item, quantity, and references to the purchase order
 * and product associated with the item.
 * <p>
 * The class uses JPA annotations to map the entity to a database table and includes validation annotations
 * to ensure that the cost and quantity are within specified ranges.
 */
@Entity
@Table(name = "purchase_order_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"purchaseOrder", "product"}, callSuper = true)
public class PurchaseOrderItem extends BaseEntity {
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
    @Min(value = 1, message = "Quantity cannot be negative")
    @Max(value = 10_000, message = "Quantity cannot exceed 10,000")
    private short quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", referencedColumnName = "id")
    @Valid
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Valid
    private Product product;
}
