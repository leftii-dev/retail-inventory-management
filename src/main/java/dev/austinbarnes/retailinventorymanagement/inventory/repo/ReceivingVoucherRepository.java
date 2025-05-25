package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * ReceivingVoucherRepository is a Spring Data JPA repository interface for managing ReceivingVoucher entities.
 * <p>
 * It extends JpaRepository, providing CRUD operations and query methods for ReceivingVoucher entities.
 * <p>
 * The repository is annotated with @Repository, indicating that it is a Spring-managed component.
 */
@Repository
public interface ReceivingVoucherRepository extends JpaRepository<ReceivingVoucher, UUID> {
    @Modifying
    @Query("UPDATE ReceivingVoucher r SET r.active = false WHERE r.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(ReceivingVoucher receivingVoucher) {
        softDeleteById(receivingVoucher.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }

}
