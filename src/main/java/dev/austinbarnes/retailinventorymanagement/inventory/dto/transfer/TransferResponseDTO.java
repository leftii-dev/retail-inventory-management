package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.LocalDate;
import java.util.UUID;

public interface TransferResponseDTO {
    UUID id();
    LocalDate date();
    String transferCode();
    LocationResponseDTO locationTo();
    LocationResponseDTO locationFrom();
}
