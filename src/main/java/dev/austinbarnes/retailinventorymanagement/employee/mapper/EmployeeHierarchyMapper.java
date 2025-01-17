package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {EmployeeMapper.class})
public interface EmployeeHierarchyMapper {
    EmployeeHierarchy toEntity(EmployeeHierarchyRequestDTO employeeHierarchyRequestDTO);

    @Named("basicEmployeeHierarchy")
    @Mapping(target = "employee", qualifiedByName = "basicEmployee")
    @Mapping(target = "manager", qualifiedByName = "basicEmployee")
    EmployeeHierarchyResponseBasicDTO toBasicDTO(EmployeeHierarchy employeeHierarchy);

    @Mapping(target = "employee", qualifiedByName = "detailEmployee")
    @Mapping(target = "manager", qualifiedByName = "detailEmployee")
    @Named("detailEmployeeHierarchy")
    EmployeeHierarchyResponseDetailDTO toDetailDTO(EmployeeHierarchy employeeHierarchy);
}
