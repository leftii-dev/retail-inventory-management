package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import java.time.LocalDate;
import java.util.UUID;

/**
 * TransferResponseBasicDTO is a Data Transfer Object (DTO) used for transferring basic transfer data.
 * <p>
 * It contains fields for transfer ID, date, transfer code, location IDs for the source and destination.
 * <p>
 * This DTO is used to represent the transfer data in a simplified format.
 */
public record TransferResponseBasicDTO(
        UUID id,
        LocalDate date,
        String transferCode,
        UUID locationToID,
        UUID locationFromID,
        UUID statusId
) implements TransferResponseDTO{
}
