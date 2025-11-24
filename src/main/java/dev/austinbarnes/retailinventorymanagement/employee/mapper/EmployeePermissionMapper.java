package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.EmployeePermissionResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeePermission;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Permission;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import dev.austinbarnes.retailinventorymanagement.employee.repo.PermissionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * EmployeePermissionMapper is an interface for mapping between EmployeePermission entities and their DTO representations.
 * It provides methods to convert EmployeePermissionRequestDTO to EmployeePermission entities,
 * as well as methods to convert EmployeePermission entities to EmployeePermissionResponseBasicDTO and EmployeePermissionResponseDetailDTO.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {EmployeeMapper.class, PermissionMapper.class})
public abstract class EmployeePermissionMapper {
    @Autowired
    protected EmployeeRepository employeeRepository;
    @Autowired
    protected PermissionRepository permissionRepository;
    /**
     * Converts an EmployeePermissionRequestDTO to an EmployeePermission entity.
     *
     * @param employeePermissionRequestDTO the EmployeePermissionRequestDTO to convert
     * @return the converted EmployeePermission entity
     */

    @Mapping(target = "employee", source = "employeeID")
    @Mapping(target = "permission", source = "permissionID")
    public abstract EmployeePermission toEntity(EmployeePermissionRequestDTO employeePermissionRequestDTO);

    /**
     * Converts an EmployeePermission entity to an EmployeePermissionResponseBasicDTO.
     *
     * @param employeePermission the EmployeePermission entity to convert
     * @return the converted EmployeePermissionResponseBasicDTO
     */
    @Named("basicEmployeePermission")
    @Mapping(target = "employee", qualifiedByName = "basicEmployee")
    @Mapping(target = "permission", qualifiedByName = "basicPermission")
    public abstract EmployeePermissionResponseBasicDTO toBasicDTO(EmployeePermission employeePermission);

    /**
     * Converts an EmployeePermission entity to an EmployeePermissionResponseDetailDTO.
     *
     * @param employeePermission the EmployeePermission entity to convert
     * @return the converted EmployeePermissionResponseDetailDTO
     */
    @Named("detailEmployeePermission")
    @Mapping(target = "employee", qualifiedByName = "detailEmployee")
    @Mapping(target = "permission", qualifiedByName = "detailPermission")
    public abstract EmployeePermissionResponseDetailDTO toDetailDTO(EmployeePermission employeePermission);

    /**
     * Updates an existing EmployeePermission entity with the values from the EmployeePermissionRequestDTO.
     *
     * @param employeePermissionRequestDTO the EmployeePermissionRequestDTO containing the new values
     * @param employeePermission           the EmployeePermission entity to update
     */
    @Mapping(target = "employee", source = "employeeID")
    @Mapping(target = "permission", source = "permissionID")
    public abstract void updateEntityFromRequest(EmployeePermissionRequestDTO employeePermissionRequestDTO, @MappingTarget EmployeePermission employeePermission);

    /**
     * Custom Resolvers
     */
    protected Employee resolveEmployee(UUID id) {
        return id == null ? null : employeeRepository.findById(id).orElse(null);
    }

    protected Permission resolvePermission(UUID id) {
        return id == null ? null : permissionRepository.findById(id).orElse(null);
    }
}
