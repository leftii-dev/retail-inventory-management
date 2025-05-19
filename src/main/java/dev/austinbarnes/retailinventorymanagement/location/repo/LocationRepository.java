package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * LocationRepository is a Spring Data JPA repository interface for managing Location entities.
 * It extends JpaRepository to provide CRUD operations and query methods for Location.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
}
