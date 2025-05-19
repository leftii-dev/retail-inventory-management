package dev.austinbarnes.retailinventorymanagement.product.repo;

import dev.austinbarnes.retailinventorymanagement.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * CategoryRepository is an interface that extends JpaRepository to provide CRUD operations for the Category entity.
 * It uses UUID as the type of the primary key.
 * This repository is used to interact with the database and perform operations on the Category entity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
