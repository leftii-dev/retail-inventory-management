package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeHierarchyResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.entity.EmployeeHierarchy;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * EmployeeHierarchyMapper is an interface for mapping between EmployeeHierarchy entities and their DTO representations.
 * It provides methods to convert EmployeeHierarchyRequestDTO to EmployeeHierarchy entities,
 * as well as methods to convert EmployeeHierarchy entities to EmployeeHierarchyResponseBasicDTO and EmployeeHierarchyResponseDetailDTO.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {EmployeeMapper.class})
public abstract class EmployeeHierarchyMapper {
    @Autowired
    protected EmployeeRepository employeeRepository;
    /**
     * Converts an EmployeeHierarchyRequestDTO to an EmployeeHierarchy entity.
     *
     * @param employeeHierarchyRequestDTO the EmployeeHierarchyRequestDTO to convert
     * @return the converted EmployeeHierarchy entity
     */
    @Mapping(target = "employee", source = "employeeID")
    @Mapping(target = "manager", source = "managerID")
    public abstract EmployeeHierarchy toEntity(EmployeeHierarchyRequestDTO employeeHierarchyRequestDTO);

    /**
     * Converts an EmployeeHierarchy entity to an EmployeeHierarchyResponseBasicDTO.
     *
     * @param employeeHierarchy the EmployeeHierarchy entity to convert
     * @return the converted EmployeeHierarchyResponseBasicDTO
     */
    @Named("basicEmployeeHierarchy")
    @Mapping(target = "employee", qualifiedByName = "basicEmployee")
    @Mapping(target = "manager", qualifiedByName = "basicEmployee")
    public abstract EmployeeHierarchyResponseBasicDTO toBasicDTO(EmployeeHierarchy employeeHierarchy);

    /**
     * Converts an EmployeeHierarchy entity to an EmployeeHierarchyResponseDetailDTO.
     *
     * @param employeeHierarchy the EmployeeHierarchy entity to convert
     * @return the converted EmployeeHierarchyResponseDetailDTO
     */
    @Mapping(target = "employee", qualifiedByName = "detailEmployee")
    @Mapping(target = "manager", qualifiedByName = "detailEmployee")
    @Named("detailEmployeeHierarchy")
    public abstract EmployeeHierarchyResponseDetailDTO toDetailDTO(EmployeeHierarchy employeeHierarchy);

    /**
     * Updates an existing EmployeeHierarchy entity with the values from the EmployeeHierarchyRequestDTO.
     *
     * @param employeeHierarchyRequestDTO the EmployeeHierarchyRequestDTO containing the new values
     * @param employeeHierarchy           the EmployeeHierarchy entity to update
     */
    @Mapping(target = "employee", source = "employeeID")
    @Mapping(target = "manager", source = "managerID")
    public abstract void updateEntityFromRequest(EmployeeHierarchyRequestDTO employeeHierarchyRequestDTO, @MappingTarget EmployeeHierarchy employeeHierarchy);

    /**
     * Custom Resolver
     */
    protected Employee resolveEmployee(UUID id) {
        return id == null ? null : employeeRepository.findById(id).orElse(null);
    }
}
