package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface EmployeeMapper {
    Employee toEntity(EmployeeRequestDTO employeeRequestDTO);

    @Named("basicEmployee")
    EmployeeResponseBasicDTO toBasicDTO(Employee employee);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(employee.getCreatedBy().getNameLast() + \", \" + employee.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(employee.getModifiedBy().getNameLast() + \", \" + employee.getModifiedBy().getNameFirst())")
    @Named("detailEmployee")
    EmployeeResponseDetailDTO toDetailDTO(Employee employee);
}
