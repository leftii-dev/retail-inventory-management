package dev.austinbarnes.retailinventorymanagement.entitycode.repo;

import dev.austinbarnes.retailinventorymanagement.entitycode.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodeEntityRepository extends JpaRepository<CodeEntity, UUID> {
    Optional<CodeEntity> findByName(String name);
}
