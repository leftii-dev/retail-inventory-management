package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TransferRepository is a Spring Data JPA repository interface for managing Transfer entities.
 * <p>
 * It extends JpaRepository, providing CRUD operations and query methods for Transfer entities.
 * <p>
 * The repository is annotated with @Repository, indicating that it is a Spring-managed component.
 */
@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {
}
