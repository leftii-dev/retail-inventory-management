package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Employee;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeMapper;
import dev.austinbarnes.retailinventorymanagement.employee.repo.EmployeeRepository;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationDetails;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * LocationDetailsMapper is an interface that defines the mapping between LocationDetails entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert LocationDetailsRequestDTO to LocationDetails entity and
 * to convert LocationDetails entity to different types of LocationDetailsResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class, EmployeeMapper.class})
public abstract class LocationDetailsMapper {
    @Autowired
    protected LocationRepository locationRepository;
    @Autowired
    protected EmployeeRepository employeeRepository;
    /**
     * Converts LocationDetailsRequestDTO to LocationDetails entity.
     *
     * @param locationDetailsRequestDTO the LocationDetailsRequestDTO to convert
     * @return the converted LocationDetails entity
     */
    @Mapping(target = "location", source = "locationID")
    @Mapping(target = "manager", source = "managerID")
    public abstract LocationDetails toEntity(LocationDetailsRequestDTO locationDetailsRequestDTO);

    /**
     * Converts LocationDetails entity to LocationDetailsResponseBasicDTO.
     */
    @Mapping(target = "manager", qualifiedByName = "basicEmployee")
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicLocationDetails")
    public abstract LocationDetailsResponseBasicDTO toBasicDTO(LocationDetails locationDetails);

    /**
     * Converts LocationDetails entity to LocationDetailsResponseDetailDTO.
     */
    @Mapping(target = "manager", qualifiedByName = "detailEmployee")
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailLocationDetails")
    public abstract LocationDetailsResponseDetailDTO toDetailDTO(LocationDetails locationDetails);

    /**
     * Updates an existing LocationDetails entity with the values from the LocationDetailsRequestDTO.
     *
     * @param locationDetailsRequestDTO the LocationDetailsRequestDTO containing the new values
     * @param locationDetails           the LocationDetails entity to update
     */
    @Mapping(target = "location", source = "locationID")
    @Mapping(target = "manager", source = "managerID")
    public abstract void updateEntityFromRequest(LocationDetailsRequestDTO locationDetailsRequestDTO, @MappingTarget LocationDetails locationDetails);

    /**
     * Custom Resolvers
     */
    protected Location resolveLocation(UUID locationID) {
        return locationID == null ? null : locationRepository.findById(locationID).orElse(null);
    }

    protected Employee resolveEmployee(UUID managerID) {
        return managerID == null ? null : employeeRepository.findById(managerID).orElse(null);
    }
}
