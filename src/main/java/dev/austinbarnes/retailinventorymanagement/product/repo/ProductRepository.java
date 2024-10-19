package dev.austinbarnes.retailinventorymanagement.product.repo;

import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
