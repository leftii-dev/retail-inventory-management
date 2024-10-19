package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {
}
