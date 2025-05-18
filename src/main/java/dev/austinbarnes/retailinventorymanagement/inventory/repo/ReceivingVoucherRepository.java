package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
