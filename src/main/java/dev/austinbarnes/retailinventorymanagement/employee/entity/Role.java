package dev.austinbarnes.retailinventorymanagement.employee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = 3, max = 60, message = "Name of role must be 3 to 60 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 3000, message = "Description cannot exceed 3000 characters")
    private String description;

    @Column(name = "created_at", updatable = false)
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

    @Column(name = "deleted", nullable = false)
    @NotNull
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
