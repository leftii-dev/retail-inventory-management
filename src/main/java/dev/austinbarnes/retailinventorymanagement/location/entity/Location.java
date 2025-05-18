package dev.austinbarnes.retailinventorymanagement.location.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Location is an entity class that represents a location in the retail inventory management system.
 * It includes fields for the name, location type, and timestamps for creation and updates.
 * The class is annotated with JPA annotations to map it to a database table.
 */
@Entity
@Table(name = "location")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class Location extends BaseEntity {
    @Column(name = "name")
    @Size(max = 100, message = "Location name cannot exceed 100 characters")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_type_id", referencedColumnName = "id")
    @Valid
    private LocationType locationType;
}
