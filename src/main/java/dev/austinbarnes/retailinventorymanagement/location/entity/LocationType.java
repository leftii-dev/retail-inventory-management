package dev.austinbarnes.retailinventorymanagement.location.entity;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "location_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

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
