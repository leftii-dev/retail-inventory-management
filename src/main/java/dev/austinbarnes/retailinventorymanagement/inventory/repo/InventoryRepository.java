package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * InventoryRepository is a Spring Data JPA repository interface for managing Inventory entities.
 * <p>
 * It extends JpaRepository, providing CRUD operations and query methods for Inventory entities.
 * <p>
 * The repository is annotated with @Repository, indicating that it is a Spring-managed component.
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    List<Inventory> findAllByProductId(UUID productId);
    List<Inventory> findAllByLocationId(UUID locationId);

    @Modifying
    @Query("UPDATE Inventory i SET i.active = false WHERE i.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(Inventory inventory) {
        softDeleteById(inventory.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
