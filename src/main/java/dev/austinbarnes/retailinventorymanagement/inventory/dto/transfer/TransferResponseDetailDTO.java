package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TransferResponseDetailDTO(
        UUID id,
        LocalDate date,
        String transferCode,
        BigDecimal totalCost,
        BigDecimal totalQuantity,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        UUID locationToID,
        UUID locationFromID,
        boolean deleted
) implements TransferResponseDTO{
}
