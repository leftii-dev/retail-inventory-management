package dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record VendorResponseDetailDTO(
        UUID id,
        String vendorCode,
        String name,
        String addressLine1,
        String addressLine2,
        String contactName,
        String phone,
        String email,
        boolean isActive,
        Instant createdAt,
        Instant modifiedAt,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        boolean deleted
) implements VendorResponseDTO{
}
