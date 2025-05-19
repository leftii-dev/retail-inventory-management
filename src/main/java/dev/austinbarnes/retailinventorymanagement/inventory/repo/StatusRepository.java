package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * StatusRepository is a Spring Data JPA repository interface for managing Status entities.
 * <p>
 * It extends JpaRepository, providing CRUD operations and query methods for Status entities.
 * <p>
 * The repository is annotated with @Repository, indicating that it is a Spring-managed component.
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {
}
