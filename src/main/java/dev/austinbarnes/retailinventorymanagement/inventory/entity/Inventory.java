package dev.austinbarnes.retailinventorymanagement.inventory.entity;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "inventory")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "quantity")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Max(value = 99999999, message = "Quantity cannot exceed 99,999,999")
    @NotNull
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location", referencedColumnName = "id")
    private Location location;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private Instant modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id", referencedColumnName = "id")
    private Employee modifiedBy;

    @Column(name = "deleted")
    private boolean deleted = false;

    @PrePersist
    private void onCreate() {
        this.createdAt = Instant.now();
        this.modifiedAt = Instant.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.modifiedAt = Instant.now();
    }
}
