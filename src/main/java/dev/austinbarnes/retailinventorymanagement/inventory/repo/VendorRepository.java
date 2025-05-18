package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * VendorRepository is a Spring Data JPA repository interface for managing Vendor entities.
 * <p>
 * It extends JpaRepository, providing CRUD operations and query methods for Vendor entities.
 * <p>
 * The repository is annotated with @Repository, indicating that it is a Spring-managed component.
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, UUID> {
}
