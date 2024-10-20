package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
