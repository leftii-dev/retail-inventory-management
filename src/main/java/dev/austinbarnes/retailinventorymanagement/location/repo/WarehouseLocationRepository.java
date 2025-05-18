package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.WarehouseLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * WarehouseLocationRepository is a Spring Data JPA repository interface for managing WarehouseLocation entities.
 * It extends JpaRepository to provide CRUD operations and query methods for WarehouseLocation.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
public interface WarehouseLocationRepository extends JpaRepository<WarehouseLocation, UUID> {
}
