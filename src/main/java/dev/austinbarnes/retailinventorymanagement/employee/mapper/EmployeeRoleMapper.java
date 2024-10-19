package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.role.EmployeeRoleRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.role.EmployeeRoleResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.role.EmployeeRoleResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.role.EmployeeRoleResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeRole;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {EmployeeMapper.class, RoleMapper.class})
public interface EmployeeRoleMapper {
    EmployeeRole toEntity(EmployeeRoleRequestDTO employeeRoleRequestDTO);

    @Named("basicEmployeeRole")
    @Mapping(target = "employee", qualifiedByName = "basicEmployee")
    @Mapping(target = "role", qualifiedByName = "basicRole")
    EmployeeRoleResponseBasicDTO toBasicDTO(EmployeeRole employeeRole);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(employeeRole.getCreatedBy().getNameLast() + \", \" + employeeRole.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(employeeRole.getModifiedBy().getNameLast() + \", \" + employeeRole.getModifiedBy().getNameFirst())")
    @Named("detailEmployeeRole")
    @Mapping(target = "employee", qualifiedByName = "detailEmployee")
    @Mapping(target = "role", qualifiedByName = "detailRole")
    EmployeeRoleResponseDetailDTO toDetailDTO(EmployeeRole employeeRole);
}
