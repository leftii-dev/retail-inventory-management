package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * LocationTypeRepository is a Spring Data JPA repository interface for managing LocationType entities.
 * It extends JpaRepository to provide CRUD operations and query methods for LocationType.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, UUID>, JpaSpecificationExecutor<LocationType> {
    @Modifying
    @Query("UPDATE LocationType l SET l.active = false WHERE l.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(LocationType locationType) {
        softDeleteById(locationType.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
