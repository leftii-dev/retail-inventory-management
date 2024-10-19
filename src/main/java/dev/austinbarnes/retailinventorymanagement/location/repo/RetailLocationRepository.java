package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.RetailLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RetailLocationRepository extends JpaRepository<RetailLocation, UUID> {
}
