package dev.austinbarnes.retailinventorymanagement.product.repo;

import dev.austinbarnes.retailinventorymanagement.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * BrandRepository is an interface that extends JpaRepository to provide CRUD operations for the Brand entity.
 * It uses UUID as the type of the primary key.
 * This repository is used to interact with the database and perform operations on the Brand entity.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
