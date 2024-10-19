package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
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

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(employeeHierarchy.getCreatedBy().getNameLast() + \", \" + employeeHierarchy.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(employeeHierarchy.getModifiedBy().getNameLast() + \", \" + employeeHierarchy.getModifiedBy().getNameFirst())")
    @Mapping(target = "employee", qualifiedByName = "detailEmployee")
    @Mapping(target = "manager", qualifiedByName = "detailEmployee")
    @Named("detailEmployeeHierarchy")
    EmployeeHierarchyResponseDetailDTO toDetailDTO(EmployeeHierarchy employeeHierarchy);
}
