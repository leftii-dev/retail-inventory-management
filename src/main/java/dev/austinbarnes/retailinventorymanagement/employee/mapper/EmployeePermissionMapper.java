package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeePermission;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {EmployeeMapper.class, PermissionMapper.class})
public interface EmployeePermissionMapper {
    EmployeePermission toEntity(EmployeePermissionRequestDTO employeePermissionRequestDTO);

    @Named("basicEmployeePermission")
    @Mapping(target = "employee", qualifiedByName = "basicEmployee")
    @Mapping(target = "permission", qualifiedByName = "basicPermission")
    EmployeePermissionResponseBasicDTO toBasicDTO(EmployeePermission employeePermission);

    @Named("detailEmployeePermission")
    @Mapping(target = "employee", qualifiedByName = "detailEmployee")
    @Mapping(target = "permission", qualifiedByName = "detailPermission")
    EmployeePermissionResponseDetailDTO toDetailDTO(EmployeePermission employeePermission);
}
