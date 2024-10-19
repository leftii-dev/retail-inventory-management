package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {
}
