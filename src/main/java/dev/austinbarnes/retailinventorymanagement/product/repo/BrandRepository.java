package dev.austinbarnes.retailinventorymanagement.product.repo;

import dev.austinbarnes.retailinventorymanagement.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * BrandRepository is an interface that extends JpaRepository to provide CRUD operations for the Brand entity.
 * It uses UUID as the type of the primary key.
 * This repository is used to interact with the database and perform operations on the Brand entity.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {
    @Modifying
    @Query("UPDATE Brand b SET b.active = false WHERE b.id = :id")
    void softDeleteById(UUID id);

    @Override
    default void delete(Brand brand) {
        softDeleteById(brand.getId());
    }

    @Override
    default void deleteById(@NonNull UUID id) {
        softDeleteById(id);
    }
}
