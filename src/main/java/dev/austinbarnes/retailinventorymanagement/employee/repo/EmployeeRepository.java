package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
