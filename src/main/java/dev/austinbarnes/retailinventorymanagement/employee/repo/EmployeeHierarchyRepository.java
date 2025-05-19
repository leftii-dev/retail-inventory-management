package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
}
