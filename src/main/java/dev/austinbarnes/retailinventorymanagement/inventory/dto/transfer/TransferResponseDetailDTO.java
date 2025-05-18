package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * TransferResponseDetailDTO is a Data Transfer Object (DTO) used for transferring detailed transfer data.
 * <p>
 * It contains fields for transfer ID, date, transfer code, total cost, total quantity, timestamps,
 * location IDs for the source and destination, and user IDs for creation and modification.
 * <p>
 * This DTO is used to represent the transfer data in a detailed format.
 */
public record TransferResponseDetailDTO(
        UUID id,
        LocalDate date,
        String transferCode,
        BigDecimal totalCost,
        BigDecimal totalQuantity,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdBy,
        UUID modifiedBy,
        UUID locationToID,
        UUID locationFromID,
        boolean active
) implements TransferResponseDTO{
}
