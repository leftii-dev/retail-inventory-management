package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * EmployeeMapper is an interface for mapping between Employee entities and their DTO representations.
 * It provides methods to convert EmployeeRequestDTO to Employee entities,
 * as well as methods to convert Employee entities to EmployeeResponseBasicDTO and EmployeeResponseDetailDTO.
 */
@Mapper(config = GlobalMapperConfig.class)
public interface EmployeeMapper {

    /**
     * Converts an EmployeeRequestDTO to an Employee entity.
     *
     * @param employeeRequestDTO the EmployeeRequestDTO to convert
     * @return the converted Employee entity
     */
    @Mapping(target = "employeeCode", ignore = true)
    @Mapping(target = "user", ignore = true)
    Employee toEntity(EmployeeRequestDTO employeeRequestDTO);

    /**
     * Converts an Employee entity to an EmployeeResponseBasicDTO.
     *
     * @param employee the Employee entity to convert
     * @return the converted EmployeeResponseBasicDTO
     */
    @Named("basicEmployee")
    EmployeeResponseBasicDTO toBasicDTO(Employee employee);

    /**
     * Converts an Employee entity to an EmployeeResponseDetailDTO.
     *
     * @param employee the Employee entity to convert
     * @return the converted EmployeeResponseDetailDTO
     */
    @Named("detailEmployee")
    EmployeeResponseDetailDTO toDetailDTO(Employee employee);
}
