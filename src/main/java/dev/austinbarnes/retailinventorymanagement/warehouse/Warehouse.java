package dev.austinbarnes.retailinventorymanagement.warehouse;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "warehouse")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //TODO: Create method for auto generated codes. Use util and another table?
    @Column(name = "warehouse_code")
    @Size(min = 6, max = 6)
    private String warehouseCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Column(name = "deleted")
    private Boolean deleted = false;

}
