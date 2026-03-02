package dev.austinbarnes.retailinventorymanagement.inventory.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Transfer is an entity class representing a transfer of products between locations in a retail management system.
 * <p>
 * It contains fields for the date of transfer, transfer code, total cost, total quantity, and references to the
 * source and destination locations.
 * <p>
 * The class uses JPA annotations to map the entity to a database table and includes validation annotations
 * to ensure that the fields are within specified ranges and constraints.
 */
@Entity
@Table(name = "transfer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"locationFrom", "locationTo"})
public class Transfer extends BaseEntity {
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "transfer_code", nullable = false, updatable = false, unique = true)
    @Size(min = 10, max = 10, message = "Transfer code should be auto-generated 10 digit string")
    @NotNull
    private String transferCode;

    @Column(name = "total_cost")
    @DecimalMax(value = "9999999999.99", message = "Total cost exceeds limit, double check entered costs")
    @DecimalMin(value = "0.00", message = "Total cost cannot be negative, double check entered costs")
    private BigDecimal totalCost;

    @Column(name = "total_qty")
    @Min(value = 1, message = "Total quantity cannot be less that one (1), double check entered quantities")
    @Max(value = 1_000_000, message = "Total quantity exceeds limit, double check entered quantities")
    private int totalQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @Valid
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_to_id", referencedColumnName = "id")
    @Valid
    private Location locationTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_from_id", referencedColumnName = "id")
    @Valid
    private Location locationFrom;
}
