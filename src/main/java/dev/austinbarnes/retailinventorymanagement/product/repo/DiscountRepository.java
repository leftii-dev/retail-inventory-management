package dev.austinbarnes.retailinventorymanagement.product.repo;

import dev.austinbarnes.retailinventorymanagement.product.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
}
