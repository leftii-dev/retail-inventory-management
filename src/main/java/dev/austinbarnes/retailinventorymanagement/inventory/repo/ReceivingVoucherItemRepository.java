package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * ReceivingVoucherItemRepository is a Spring Data JPA repository interface for managing ReceivingVoucherItem entities.
 * <p>
 * It extends JpaRepository, providing CRUD operations and query methods for ReceivingVoucherItem entities.
 * <p>
 * The repository is annotated with @Repository, indicating that it is a Spring-managed component.
 */
@Repository
public interface ReceivingVoucherItemRepository extends JpaRepository<ReceivingVoucherItem, UUID> {
}
