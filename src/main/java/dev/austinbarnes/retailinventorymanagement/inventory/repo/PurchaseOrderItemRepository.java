package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, UUID> {
}
