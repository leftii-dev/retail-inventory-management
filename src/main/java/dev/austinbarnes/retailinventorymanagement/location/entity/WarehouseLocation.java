package dev.austinbarnes.retailinventorymanagement.location.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * RetailLocation is an entity class that represents a retail location in the retail inventory management system.
 * It includes fields for the retail location code and a reference to the location.
 * The class is annotated with JPA annotations to map it to a database table.
 */
@Entity
@Table(name = "warehouse")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"location"})
public class WarehouseLocation extends BaseEntity {
    @Column(name = "warehouse_code", nullable = false, updatable = false, unique = true)
    @NotNull
    private String warehouseCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @Valid
    private Location location;
}
