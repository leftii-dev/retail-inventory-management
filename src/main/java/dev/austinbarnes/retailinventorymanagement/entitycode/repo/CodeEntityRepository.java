package dev.austinbarnes.retailinventorymanagement.entitycode.repo;

import dev.austinbarnes.retailinventorymanagement.entitycode.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * CodeEntityRepository is a Spring Data JPA repository interface for managing
 * CodeEntity entities.
 * <p>
 * It provides methods to perform CRUD operations and custom queries on the code entity data.
 */
@Repository
public interface CodeEntityRepository extends JpaRepository<CodeEntity, UUID> {
    Optional<CodeEntity> findByName(String name);

    @Modifying
    @Query("UPDATE CodeEntity c SET c.active = false WHERE c.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(CodeEntity codeEntity) {
        softDeleteById(codeEntity.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
