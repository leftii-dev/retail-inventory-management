package dev.austinbarnes.retailinventorymanagement.product.repo;

import dev.austinbarnes.retailinventorymanagement.product.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * DiscountRepository is an interface that extends JpaRepository to provide CRUD operations for the Discount entity.
 * It uses UUID as the type of the primary key.
 * This repository is used to interact with the database and perform operations on the Discount entity.
 */
@Repository
public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    @Modifying
    @Query("UPDATE Discount d SET d.active = false WHERE d.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(Discount discount) {
        softDeleteById(discount.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
