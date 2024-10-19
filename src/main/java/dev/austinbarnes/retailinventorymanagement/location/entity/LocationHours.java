package dev.austinbarnes.retailinventorymanagement.location.entity;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "location_hours")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationHours {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamptz")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "modified_at", nullable = false, updatable = false, columnDefinition = "timestamptz")
    @UpdateTimestamp
    private Instant modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false, referencedColumnName = "id")
    Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by", nullable = false, updatable = false, referencedColumnName = "id")
    Employee modifiedBy;

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
