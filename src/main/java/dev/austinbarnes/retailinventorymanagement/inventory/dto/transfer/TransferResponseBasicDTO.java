package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.LocalDate;
import java.util.UUID;

public record TransferResponseBasicDTO(
        UUID id,
        LocalDate date,
        String transferCode,
        UUID locationToID,
        UUID locationFromID
) implements TransferResponseDTO{
}
