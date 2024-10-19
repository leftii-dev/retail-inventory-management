package dev.austinbarnes.retailinventorymanagement.product.repo;

import dev.austinbarnes.retailinventorymanagement.product.entity.CategoryHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryHierarchyRepository extends JpaRepository<CategoryHierarchy, UUID> {
}
