package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.role.EmployeeRoleRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.role.EmployeeRoleResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.role.EmployeeRoleResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeRoleMapper {
    EmployeeRole toEntity(EmployeeRoleRequestDTO employeeRoleRequestDTO);
    EmployeeRoleResponseBasicDTO toBasicDTO(EmployeeRole employeeRole);
    EmployeeRoleResponseDetailDTO toRequestDTO(EmployeeRole employeeRole);
}
