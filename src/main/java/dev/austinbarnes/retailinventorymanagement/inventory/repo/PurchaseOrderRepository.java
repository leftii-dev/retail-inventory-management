package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {
}
