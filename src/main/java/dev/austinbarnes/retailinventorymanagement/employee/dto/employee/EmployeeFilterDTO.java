package dev.austinbarnes.retailinventorymanagement.employee.dto.employee;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EmployeeFilterDTO(
        BaseFilterDTO baseFilterDTO,
        UUID user,
        @Size(min = 1, max = 50, message = "First name search must be between 1 and 50 characters.")
        String firstNameContains,
        @Size(min = 1, max = 50, message = "Last name search must be between 1 and 50 characters.")
        String lastNameContains,
        @Size(min = 1, max = 100, message = "Email search must be between 1 and 100 characters.")
        String emailContains,
        @Size(min = 1, max = 10, message = "Employee code search must be 10 or less characters.")
        String employeeCodeContains,
        Boolean showNonCurrentEmployee,
        @Size(min = 3, max = 50, message = "Permission search must be 3 to 50 characters.")
        String hasPermission
) implements FilterDTO
{}
