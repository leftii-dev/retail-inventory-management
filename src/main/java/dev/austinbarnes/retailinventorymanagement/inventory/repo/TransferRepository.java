package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
}
