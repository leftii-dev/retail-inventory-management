package dev.austinbarnes.retailinventorymanagement.product.repo;

import dev.austinbarnes.retailinventorymanagement.product.entity.CategoryHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * CategoryHierarchyRepository is an interface that extends JpaRepository to provide CRUD operations for the CategoryHierarchy entity.
 * It uses UUID as the type of the primary key.
 * This repository is used to interact with the database and perform operations on the CategoryHierarchy entity.
 */
@Repository
public interface CategoryHierarchyRepository extends JpaRepository<CategoryHierarchy, UUID> {
}
