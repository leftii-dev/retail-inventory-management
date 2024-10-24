package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import java.time.LocalDate;
import java.util.UUID;

public interface TransferResponseDTO {
    UUID id();
    LocalDate date();
    String transferCode();
    UUID locationToID();
    UUID locationFromID();
}
