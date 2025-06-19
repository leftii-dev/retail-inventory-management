package dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

public record VendorFilterDTO(
        BaseFilterDTO baseFilterDTO,
        @Size(min = 3, max = 10, message = "Vendor code search string must be between 3 and 10 characters")
        String codeContains,
        @Size(min = 2, max = 100, message = "Vendor name search string must be between 2 and 100 characters")
        String nameContains,
        @Size(min = 3, max = 60, message = "Address search string must be between 3 and 60 characters")
        String addressContains,
        @Size(min = 2, max = 60, message = "Contact name search string must be between 2 and 60 characters")
        String contactContains,
        @Size(min = 3, max = 10, message = "Phone search string must be between 3 and 10 characters")
        String phoneContains,
        @Size(min = 5, max = 100, message = "Email search string must be between 5 and 100 characters")
        String emailContains
) implements FilterDTO {
}
