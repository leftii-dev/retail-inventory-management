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
@Table(name = "transfer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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
    @Max(value = 10000, message = "Total quantity exceeds limit, double check entered quantities")
    private int totalQuantity;

    @Column(name = "created_at", updatable = false, columnDefinition = "timestamptz")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "modified_at", columnDefinition = "timestamptz")
    @UpdateTimestamp
    private Instant modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    @Valid
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id", referencedColumnName = "id")
    @Valid
    private Employee modifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_to_id", referencedColumnName = "id")
    @Valid
    private Location locationTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_from_id", referencedColumnName = "id")
    @Valid
    private Location locationFrom;

    @Column(name = "deleted")
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
