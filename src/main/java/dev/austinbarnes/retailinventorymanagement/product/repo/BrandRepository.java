package dev.austinbarnes.retailinventorymanagement.product.repo;

import dev.austinbarnes.retailinventorymanagement.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
