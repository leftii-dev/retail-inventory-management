package dev.austinbarnes.retailinventorymanagement.inventory.repo;

import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReceivingVoucherRepository extends JpaRepository<ReceivingVoucher, UUID> {
}
