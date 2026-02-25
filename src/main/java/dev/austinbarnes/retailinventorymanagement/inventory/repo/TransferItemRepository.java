package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.TransferItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * TransferItemRepository is a Spring Data JPA repository interface for managing TransferItem entities.
 * <p>
 * It extends JpaRepository, providing CRUD operations and query methods for TransferItem entities.
 * <p>
 * The repository is annotated with @Repository, indicating that it is a Spring-managed component.
 */
@Repository
public interface TransferItemRepository extends JpaRepository<TransferItem, UUID>, JpaSpecificationExecutor<TransferItem> {
    List<TransferItem> findAllByTransfer_IdAndActiveTrue(UUID transferId);

    @Modifying
    @Query("UPDATE TransferItem t SET t.active = false WHERE t.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(TransferItem transferItem) {
        softDeleteById(transferItem.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
