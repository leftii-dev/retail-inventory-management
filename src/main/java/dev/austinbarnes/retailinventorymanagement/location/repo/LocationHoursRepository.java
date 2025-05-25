package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.LocationHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * LocationHoursRepository is a Spring Data JPA repository interface for managing LocationHours entities.
 * It extends JpaRepository to provide CRUD operations and query methods for LocationHours.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
public interface LocationHoursRepository extends JpaRepository<LocationHours, UUID> {
    @Modifying
    @Query("UPDATE LocationHours l SET l.active = false WHERE l.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(LocationHours locationHours) {
        softDeleteById(locationHours.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
