package dev.austinbarnes.retailinventorymanagement.location.dto;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record LocationResponseAdminDTO(
    UUID id,
    String name,
    Instant createdAt,
    Instant modifiedAt,
    LocationTypeResponseDTO locationType,
    EmployeeResponseDTO createdBy,
    EmployeeResponseDTO modifiedBy,
    boolean deleted
) implements LocationResponseDTO{
}
