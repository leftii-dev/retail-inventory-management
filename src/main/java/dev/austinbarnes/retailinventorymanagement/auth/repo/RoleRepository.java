package dev.austinbarnes.retailinventorymanagement.auth.repo;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
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

    @Modifying
    @Query("UPDATE Role r SET r.active = false WHERE r.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(Role role) {
        softDeleteById(role.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
        }

}
