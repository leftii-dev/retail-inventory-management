package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * EmployeePermissionRepository is a Spring Data JPA repository interface for managing
 * EmployeePermission entities.
 * <p>
 * It provides methods to perform CRUD operations and custom queries on the employee permission data.
 */
@Repository
public interface EmployeePermissionRepository extends JpaRepository<EmployeePermission, UUID>, JpaSpecificationExecutor<EmployeePermission> {
    List<EmployeePermission> findAllByEmployeeId(UUID employeeId);

    @Modifying
    @Query("UPDATE EmployeePermission e SET e.active = false WHERE e.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(EmployeePermission employeePermission) {
        softDeleteById(employeePermission.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
