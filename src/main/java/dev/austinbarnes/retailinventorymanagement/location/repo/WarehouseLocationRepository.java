package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.WarehouseLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarehouseLocationRepository extends JpaRepository<WarehouseLocation, UUID> {
}
