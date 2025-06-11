package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * PurchaseOrderItemRepository is a Spring Data JPA repository interface for managing PurchaseOrderItem entities.
 * <p>
 * It extends JpaRepository, providing CRUD operations and query methods for PurchaseOrderItem entities.
 * <p>
 * The repository is annotated with @Repository, indicating that it is a Spring-managed component.
 */
@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, UUID>, JpaSpecificationExecutor<PurchaseOrderItem> {
    @Modifying
    @Query("UPDATE PurchaseOrderItem p SET p.active = false WHERE p.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(PurchaseOrderItem purchaseOrderItem) {
        softDeleteById(purchaseOrderItem.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
