package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeHierarchyRepository extends JpaRepository<EmployeeHierarchy, UUID> {
}
