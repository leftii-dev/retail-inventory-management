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
@Table(name = "retail_location")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"location"})
public class RetailLocation extends BaseEntity {
    @Column(name = "retail_location_code", nullable = false, unique = true, updatable = false)
    @NotNull
    private String retailLocationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @Valid
    private Location location;
}
