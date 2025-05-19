package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * EmployeeRepository is a Spring Data JPA repository interface for managing
 * Employee entities.
 * <p>
 * It provides methods to perform CRUD operations and custom queries on the employee data.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByEmployeeCode(String employeeCode);
    Optional<Employee> findByUserId(UUID userId);
    List<Employee> findAllByActiveTrue();
    List<Employee> findAllByOrderByActiveDescNameLastAsc();

}
