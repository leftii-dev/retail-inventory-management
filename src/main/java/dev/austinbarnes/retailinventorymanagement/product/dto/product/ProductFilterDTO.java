package dev.austinbarnes.retailinventorymanagement.product.dto.product;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 2, max = 20, message = "Sku search string must be between 2 and 20 characters")
        String skuContains,
        @Size(min = 2, max = 10, message = "Product code search string must be between 2 and 10 characters")
        String codeContains,
        @Size(min = 2, max = 50, message = "Query search string must be between 2 and 50 characters")
        String query,
        @DecimalMax(value = "9999999999.99", message = "Cost cannot exceed $9,999,999,999.99")
        @DecimalMin(value = "0.01", message = "Cost cannot be less than $0.01")
        @Digits(integer = 10, fraction = 2)
        BigDecimal costEqual,
        @DecimalMax(value = "9999999999.99", message = "Price cannot exceed $9,999,999,999.99")
        @DecimalMin(value = "0.01", message = "Price cannot be less than $0.01")
        @Digits(integer = 10, fraction = 2)
        BigDecimal costBelow,
        @DecimalMax(value = "9999999999.99", message = "Price cannot exceed $9,999,999,999.99")
        @DecimalMin(value = "0.01", message = "Price cannot be less than $0.01")
        @Digits(integer = 10, fraction = 2)
        BigDecimal costAbove,
        @DecimalMax(value = "9999999999.99", message = "Price cannot exceed $9,999,999,999.99")
        @DecimalMin(value = "0.01", message = "Price cannot be less than $0.01")
        @Digits(integer = 10, fraction = 2)
        BigDecimal priceEqual,
        @DecimalMax(value = "9999999999.99", message = "Price cannot exceed $9,999,999,999.99")
        @DecimalMin(value = "0.01", message = "Price cannot be less than $0.01")
        @Digits(integer = 10, fraction = 2)
        BigDecimal priceBelow,
        @DecimalMax(value = "9999999999.99", message = "Price cannot exceed $9,999,999,999.99")
        @DecimalMin(value = "0.01", message = "Price cannot be less than $0.01")
        @Digits(integer = 10, fraction = 2)
        BigDecimal priceAbove,
        UUID category,
        UUID brand,
        UUID discount
) implements FilterDTO {

}
