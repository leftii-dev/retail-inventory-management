package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * PermissionRepository is a Spring Data JPA repository interface for managing
 * Permission entities.
 * <p>
 * It provides methods to perform CRUD operations and custom queries on the permission data.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}
