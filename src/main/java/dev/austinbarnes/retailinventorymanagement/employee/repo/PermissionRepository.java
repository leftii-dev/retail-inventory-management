package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * PermissionRepository is a Spring Data JPA repository interface for managing
 * Permission entities.
 * <p>
 * It provides methods to perform CRUD operations and custom queries on the permission data.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID>, JpaSpecificationExecutor<Permission> {
    @Modifying
    @Query("UPDATE Permission p SET p.active = false WHERE p.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(Permission permission) {
        softDeleteById(permission.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
