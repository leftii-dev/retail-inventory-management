package dev.austinbarnes.retailinventorymanagement.location.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "deleted")
    private boolean deleted = false;
}
