package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * LocationRepository is a Spring Data JPA repository interface for managing Location entities.
 * It extends JpaRepository to provide CRUD operations and query methods for Location.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> , JpaSpecificationExecutor<Location> {
    Optional<Location> findByName(String name);
    @Modifying
    @Query("UPDATE Location l SET l.active = false WHERE l.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(Location location) {
        softDeleteById(location.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
