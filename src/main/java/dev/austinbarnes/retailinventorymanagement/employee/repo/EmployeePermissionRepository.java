package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * EmployeePermissionRepository is a Spring Data JPA repository interface for managing
 * EmployeePermission entities.
 * <p>
 * It provides methods to perform CRUD operations and custom queries on the employee permission data.
 */
@Repository
public interface EmployeePermissionRepository extends JpaRepository<EmployeePermission, UUID> {
}
