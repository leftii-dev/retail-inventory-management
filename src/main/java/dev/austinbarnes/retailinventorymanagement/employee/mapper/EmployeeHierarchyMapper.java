package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * EmployeeHierarchyMapper is an interface for mapping between EmployeeHierarchy entities and their DTO representations.
 * It provides methods to convert EmployeeHierarchyRequestDTO to EmployeeHierarchy entities,
 * as well as methods to convert EmployeeHierarchy entities to EmployeeHierarchyResponseBasicDTO and EmployeeHierarchyResponseDetailDTO.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {EmployeeMapper.class})
public interface EmployeeHierarchyMapper {
    /**
     * Converts an EmployeeHierarchyRequestDTO to an EmployeeHierarchy entity.
     *
     * @param employeeHierarchyRequestDTO the EmployeeHierarchyRequestDTO to convert
     * @return the converted EmployeeHierarchy entity
     */
    EmployeeHierarchy toEntity(EmployeeHierarchyRequestDTO employeeHierarchyRequestDTO);

    /**
     * Converts an EmployeeHierarchy entity to an EmployeeHierarchyResponseBasicDTO.
     *
     * @param employeeHierarchy the EmployeeHierarchy entity to convert
     * @return the converted EmployeeHierarchyResponseBasicDTO
     */
    @Named("basicEmployeeHierarchy")
    @Mapping(target = "employee", qualifiedByName = "basicEmployee")
    @Mapping(target = "manager", qualifiedByName = "basicEmployee")
    EmployeeHierarchyResponseBasicDTO toBasicDTO(EmployeeHierarchy employeeHierarchy);

    /**
     * Converts an EmployeeHierarchy entity to an EmployeeHierarchyResponseDetailDTO.
     *
     * @param employeeHierarchy the EmployeeHierarchy entity to convert
     * @return the converted EmployeeHierarchyResponseDetailDTO
     */
    @Mapping(target = "employee", qualifiedByName = "detailEmployee")
    @Mapping(target = "manager", qualifiedByName = "detailEmployee")
    @Named("detailEmployeeHierarchy")
    EmployeeHierarchyResponseDetailDTO toDetailDTO(EmployeeHierarchy employeeHierarchy);

    /**
     * Updates an existing EmployeeHierarchy entity with the values from the EmployeeHierarchyRequestDTO.
     *
     * @param employeeHierarchyRequestDTO the EmployeeHierarchyRequestDTO containing the new values
     * @param employeeHierarchy           the EmployeeHierarchy entity to update
     */
    void updateEntityFromRequest(EmployeeHierarchyRequestDTO employeeHierarchyRequestDTO, @MappingTarget EmployeeHierarchy employeeHierarchy);
}
