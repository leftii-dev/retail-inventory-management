package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * LocationTypeRepository is a Spring Data JPA repository interface for managing LocationType entities.
 * It extends JpaRepository to provide CRUD operations and query methods for LocationType.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, UUID> {
}
