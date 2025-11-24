package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.employee.EmployeeResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * EmployeeMapper is an interface for mapping between Employee entities and their DTO representations.
 * It provides methods to convert EmployeeRequestDTO to Employee entities,
 * as well as methods to convert Employee entities to EmployeeResponseBasicDTO and EmployeeResponseDetailDTO.
 */
@Mapper(config = GlobalMapperConfig.class)
public abstract class EmployeeMapper {
    @Autowired
    protected UserRepository userRepository;

    /**
     * Converts an EmployeeRequestDTO to an Employee entity.
     *
     * @param employeeRequestDTO the EmployeeRequestDTO to convert
     * @return the converted Employee entity
     */
    @Mapping(target = "employeeCode", ignore = true)
    @Mapping(target = "user", source = "userID")
    public abstract Employee toEntity(EmployeeRequestDTO employeeRequestDTO);

    /**
     * Converts an Employee entity to an EmployeeResponseBasicDTO.
     *
     * @param employee the Employee entity to convert
     * @return the converted EmployeeResponseBasicDTO
     */
    @Named("basicEmployee")
    public abstract EmployeeResponseBasicDTO toBasicDTO(Employee employee);

    /**
     * Converts an Employee entity to an EmployeeResponseDetailDTO.
     *
     * @param employee the Employee entity to convert
     * @return the converted EmployeeResponseDetailDTO
     */
    @Named("detailEmployee")
    public abstract EmployeeResponseDetailDTO toDetailDTO(Employee employee);

    /**
     * Updates an existing Employee entity with the values from the EmployeeRequestDTO.
     *
     * @param employeeRequestDTO the EmployeeRequestDTO containing the new values
     * @param employee           the Employee entity to update
     */
    @Mapping(target = "employeeCode", ignore = true)
    @Mapping(target = "user", source = "userID")
    public abstract void updateEntityFromRequest(EmployeeRequestDTO employeeRequestDTO, @MappingTarget Employee employee);

    /**
     * Custom Resolvers
     */
    protected User resolveUser(UUID id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }
}
