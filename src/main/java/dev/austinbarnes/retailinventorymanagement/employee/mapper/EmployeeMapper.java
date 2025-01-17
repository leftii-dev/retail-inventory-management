package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface EmployeeMapper {

    @Mapping(target = "employeeCode", ignore = true)
    @Mapping(target = "user", ignore = true)
    Employee toEntity(EmployeeRequestDTO employeeRequestDTO);

    @Named("basicEmployee")
    EmployeeResponseBasicDTO toBasicDTO(Employee employee);

    @Named("detailEmployee")
    EmployeeResponseDetailDTO toDetailDTO(Employee employee);
}
