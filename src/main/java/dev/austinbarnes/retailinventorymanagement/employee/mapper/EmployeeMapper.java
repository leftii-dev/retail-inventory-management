package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEntity(EmployeeRequestDTO employeeRequestDTO);
    EmployeeResponseBasicDTO toBasicDTO(Employee employee);
    EmployeeResponseDetailDTO toDetailDTO(Employee employee);
}
