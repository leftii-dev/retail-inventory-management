package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * PurchaseOrderRepository is a Spring Data JPA repository interface for managing PurchaseOrder entities.
 * <p>
 * It extends JpaRepository, providing CRUD operations and query methods for PurchaseOrder entities.
 * <p>
 * The repository is annotated with @Repository, indicating that it is a Spring-managed component.
 */
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {
}
