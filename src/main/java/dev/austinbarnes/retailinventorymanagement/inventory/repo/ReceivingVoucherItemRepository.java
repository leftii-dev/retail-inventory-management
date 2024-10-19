package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReceivingVoucherItemRepository extends JpaRepository<ReceivingVoucherItem, UUID> {
}
