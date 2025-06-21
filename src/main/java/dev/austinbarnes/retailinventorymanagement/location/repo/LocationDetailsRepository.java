package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.LocationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * LocationDetailsRepository is a Spring Data JPA repository interface for managing LocationDetails entities.
 * It extends JpaRepository to provide CRUD operations and query methods for LocationDetails.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
public interface LocationDetailsRepository extends JpaRepository<LocationDetails, UUID>, JpaSpecificationExecutor<LocationDetails> {
    @Modifying
    @Query("UPDATE LocationDetails l SET l.active = false WHERE l.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(LocationDetails locationDetails) {
        softDeleteById(locationDetails.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
