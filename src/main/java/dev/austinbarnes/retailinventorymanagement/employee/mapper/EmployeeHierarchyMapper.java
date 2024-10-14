package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeHierarchyMapper {
    EmployeeHierarchy toEntity(EmployeeHierarchyRequestDTO employeeHierarchyRequestDTO);
    EmployeeHierarchyResponseBasicDTO toBasicDTO(EmployeeHierarchy employeeHierarchy);
    EmployeeHierarchyResponseDTO toDetailDTO(EmployeeHierarchy employeeHierarchy);
}
