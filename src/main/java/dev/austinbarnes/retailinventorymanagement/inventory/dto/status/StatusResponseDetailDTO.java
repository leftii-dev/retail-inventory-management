package dev.austinbarnes.retailinventorymanagement.inventory.dto.status;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record StatusResponseDetailDTO(
        UUID id,
        String name,
        String description,
        Instant createdAt,
        Instant modifiedAt,
        UUID createdByID,
        String createdByName,
        UUID modifiedByID,
        String modifiedByName,
        boolean deleted
) implements StatusResponseDTO{
}
