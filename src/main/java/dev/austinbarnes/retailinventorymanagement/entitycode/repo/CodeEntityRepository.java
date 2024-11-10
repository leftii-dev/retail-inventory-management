package dev.austinbarnes.retailinventorymanagement.entitycode.repo;

import dev.austinbarnes.retailinventorymanagement.entitycode.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CodeEntityRepository extends JpaRepository<CodeEntity, UUID> {
    Optional<CodeEntity> findByName(String name);
}
