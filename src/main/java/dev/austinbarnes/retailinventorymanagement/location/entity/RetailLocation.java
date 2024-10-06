package dev.austinbarnes.retailinventorymanagement.location.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "deleted")
    private boolean deleted = false;
}
