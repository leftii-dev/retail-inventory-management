package dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

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
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        LocationResponseDTO locationTo,
        LocationResponseDTO locationFrom,
        boolean deleted
) implements TransferResponseDTO{
}
