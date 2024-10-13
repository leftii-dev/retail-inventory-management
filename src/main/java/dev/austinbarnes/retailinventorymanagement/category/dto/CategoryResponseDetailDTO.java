package dev.austinbarnes.retailinventorymanagement.category.dto;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record CategoryResponseDetailDTO(
        UUID id,
        String categoryCode,
        String name,
        String description,
        Instant createdAt,
        Instant modifiedAt,
        EmployeeResponseDTO createdBy,
        EmployeeResponseDTO modifiedBy,
        DiscountResponseDTO discount,
        boolean deleted
) implements CategoryResponseDTO {
}
