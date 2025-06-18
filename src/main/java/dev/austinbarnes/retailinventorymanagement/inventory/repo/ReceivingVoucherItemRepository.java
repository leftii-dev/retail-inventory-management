package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
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
public interface ReceivingVoucherItemRepository extends JpaRepository<ReceivingVoucherItem, UUID>, JpaSpecificationExecutor<ReceivingVoucherItem> {
    @Modifying
    @Query("UPDATE ReceivingVoucherItem r SET r.active = false WHERE r.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(ReceivingVoucherItem receivingVoucherItem) {
        softDeleteById(receivingVoucherItem.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
