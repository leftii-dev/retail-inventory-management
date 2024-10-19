package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, UUID> {
}
