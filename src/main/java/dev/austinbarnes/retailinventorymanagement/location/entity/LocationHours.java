package dev.austinbarnes.retailinventorymanagement.location.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalTime;

/**
 * LocationHours is an entity class that represents the hours of operation for a location in the retail inventory management system.
 * It includes fields for the day of the week, open time, close time, and a reference to the location.
 * The class is annotated with JPA annotations to map it to a database table.
 */
@Entity
@Table(name = "location_hours")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"location"})
public class LocationHours extends BaseEntity {
    @Column(name = "day_of_week")
    @Min(value = 0, message = "Day of week value cannot be negative (0-6)")
    @Max(value = 7, message = "Day of week value cannot be over 6 (0-6)")
    private short dayOfWeek;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @Valid
    private Location location;
}
