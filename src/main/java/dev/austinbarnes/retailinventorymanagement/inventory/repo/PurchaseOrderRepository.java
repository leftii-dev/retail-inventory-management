package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
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
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID>, JpaSpecificationExecutor<PurchaseOrder> {
    @Modifying
    @Query("UPDATE PurchaseOrder p SET p.active = false WHERE p.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(PurchaseOrder purchaseOrder) {
        softDeleteById(purchaseOrder.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
