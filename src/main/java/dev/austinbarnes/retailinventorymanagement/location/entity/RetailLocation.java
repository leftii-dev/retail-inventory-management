package dev.austinbarnes.retailinventorymanagement.location.entity;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
@Table(name = "retail_location")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RetailLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "retail_location_code", nullable = false, unique = true, updatable = false)
    @NotNull
    private String retailLocationCode;

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
}
