package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.RetailLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * RetailLocationRepository is a Spring Data JPA repository interface for managing RetailLocation entities.
 * It extends JpaRepository to provide CRUD operations and query methods for RetailLocation.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
public interface RetailLocationRepository extends JpaRepository<RetailLocation, UUID> {
    @Modifying
    @Query("UPDATE RetailLocation r SET r.active = false WHERE r.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(RetailLocation retailLocation) {
        softDeleteById(retailLocation.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
