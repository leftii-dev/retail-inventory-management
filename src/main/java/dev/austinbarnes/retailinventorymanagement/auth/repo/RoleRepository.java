package dev.austinbarnes.retailinventorymanagement.auth.repo;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * RoleRepository is a Spring Data JPA repository for managing Role entities.
 * It provides methods to find a role by its name.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
}
