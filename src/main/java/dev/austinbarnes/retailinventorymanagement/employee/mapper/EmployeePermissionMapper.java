package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeePermission;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * EmployeePermissionMapper is an interface for mapping between EmployeePermission entities and their DTO representations.
 * It provides methods to convert EmployeePermissionRequestDTO to EmployeePermission entities,
 * as well as methods to convert EmployeePermission entities to EmployeePermissionResponseBasicDTO and EmployeePermissionResponseDetailDTO.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {EmployeeMapper.class, PermissionMapper.class})
public interface EmployeePermissionMapper {
    /**
     * Converts an EmployeePermissionRequestDTO to an EmployeePermission entity.
     *
     * @param employeePermissionRequestDTO the EmployeePermissionRequestDTO to convert
     * @return the converted EmployeePermission entity
     */
    EmployeePermission toEntity(EmployeePermissionRequestDTO employeePermissionRequestDTO);

    /**
     * Converts an EmployeePermission entity to an EmployeePermissionResponseBasicDTO.
     *
     * @param employeePermission the EmployeePermission entity to convert
     * @return the converted EmployeePermissionResponseBasicDTO
     */
    @Named("basicEmployeePermission")
    @Mapping(target = "employee", qualifiedByName = "basicEmployee")
    @Mapping(target = "permission", qualifiedByName = "basicPermission")
    EmployeePermissionResponseBasicDTO toBasicDTO(EmployeePermission employeePermission);

    /**
     * Converts an EmployeePermission entity to an EmployeePermissionResponseDetailDTO.
     *
     * @param employeePermission the EmployeePermission entity to convert
     * @return the converted EmployeePermissionResponseDetailDTO
     */
    @Named("detailEmployeePermission")
    @Mapping(target = "employee", qualifiedByName = "detailEmployee")
    @Mapping(target = "permission", qualifiedByName = "detailPermission")
    EmployeePermissionResponseDetailDTO toDetailDTO(EmployeePermission employeePermission);
}
