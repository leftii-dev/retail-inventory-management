package dev.austinbarnes.retailinventorymanagement.employee.repo;

import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, UUID> {
}
