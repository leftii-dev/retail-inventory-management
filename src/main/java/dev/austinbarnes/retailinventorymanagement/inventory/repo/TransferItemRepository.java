package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.TransferItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TransferItemRepository is a Spring Data JPA repository interface for managing TransferItem entities.
 * <p>
 * It extends JpaRepository, providing CRUD operations and query methods for TransferItem entities.
 * <p>
 * The repository is annotated with @Repository, indicating that it is a Spring-managed component.
 */
@Repository
public interface TransferItemRepository extends JpaRepository<TransferItem, UUID> {
}
