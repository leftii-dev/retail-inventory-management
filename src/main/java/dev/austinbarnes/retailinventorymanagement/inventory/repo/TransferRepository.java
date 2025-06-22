package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
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
public interface TransferRepository extends JpaRepository<Transfer, UUID>, JpaSpecificationExecutor<Transfer> {
    @Modifying
    @Query("UPDATE Transfer t SET t.active = false WHERE t.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(Transfer transfer) {
        softDeleteById(transfer.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
