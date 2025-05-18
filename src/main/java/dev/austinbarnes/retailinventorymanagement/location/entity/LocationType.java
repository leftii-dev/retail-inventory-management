package dev.austinbarnes.retailinventorymanagement.location.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * LocationType is an entity class that represents the type of a location in the retail inventory management system.
 * It includes fields for the name of the location type.
 * The class is annotated with JPA annotations to map it to a database table.
 */
@Entity
@Table(name = "location_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class LocationType extends BaseEntity {
    @Column(name = "name")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;
}
