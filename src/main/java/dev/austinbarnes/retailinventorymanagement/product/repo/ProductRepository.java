package dev.austinbarnes.retailinventorymanagement.product.repo;

import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * ProductRepository is an interface that extends JpaRepository to provide CRUD operations for the Product entity.
 * It uses UUID as the type of the primary key.
 * This repository is used to interact with the database and perform operations on the Product entity.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
