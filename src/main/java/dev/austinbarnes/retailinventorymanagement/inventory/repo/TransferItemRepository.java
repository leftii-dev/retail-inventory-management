package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.TransferItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferItemRepository extends JpaRepository<TransferItem, UUID> {
}
