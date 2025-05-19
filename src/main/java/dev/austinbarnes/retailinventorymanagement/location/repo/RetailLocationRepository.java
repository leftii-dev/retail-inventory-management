package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.RetailLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * RetailLocationRepository is a Spring Data JPA repository interface for managing RetailLocation entities.
 * It extends JpaRepository to provide CRUD operations and query methods for RetailLocation.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
public interface RetailLocationRepository extends JpaRepository<RetailLocation, UUID> {
}
