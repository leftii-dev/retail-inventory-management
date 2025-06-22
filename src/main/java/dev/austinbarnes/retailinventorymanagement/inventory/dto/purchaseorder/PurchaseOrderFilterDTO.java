package dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PurchaseOrderFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 3, max = 10, message = "Purchase Order code search must be 3 to 10 characters")
        String codeContains,
        LocalDate dateExpected,
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Total cost cannot be over $9999999999.99, double check costs")
        BigDecimal totalLessThan,
        @DecimalMin(value = "0.00", message = "Total cost cannot be negative, double check costs")
        @DecimalMax(value = "9999999999.99", message = "Total cost cannot be over $9999999999.99, double check costs")
        BigDecimal totalGreaterThan,
        UUID vendor,
        UUID status
) implements FilterDTO {
}
