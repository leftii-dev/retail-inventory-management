package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * EmployeeHierarchyRepository is a Spring Data JPA repository interface for managing
 * EmployeeHierarchy entities.
 * <p>
 * It provides methods to perform CRUD operations and custom queries on the employee hierarchy data.
 */
@Repository
public interface EmployeeHierarchyRepository extends JpaRepository<EmployeeHierarchy, UUID> {
    Optional<EmployeeHierarchy> findByEmployeeId(UUID employeeId);

    @Modifying
    @Query("UPDATE EmployeeHierarchy e SET e.active = false WHERE e.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(EmployeeHierarchy employeeHierarchy) {
        softDeleteById(employeeHierarchy.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
