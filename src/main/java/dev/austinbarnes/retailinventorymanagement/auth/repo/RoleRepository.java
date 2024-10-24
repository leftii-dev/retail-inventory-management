package dev.austinbarnes.retailinventorymanagement.auth.repo;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
