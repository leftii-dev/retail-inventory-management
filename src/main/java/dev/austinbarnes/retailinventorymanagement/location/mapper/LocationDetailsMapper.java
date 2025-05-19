package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeMapper;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * LocationDetailsMapper is an interface that defines the mapping between LocationDetails entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert LocationDetailsRequestDTO to LocationDetails entity and
 * to convert LocationDetails entity to different types of LocationDetailsResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class, EmployeeMapper.class})
public interface LocationDetailsMapper {
    /**
     * Converts LocationDetailsRequestDTO to LocationDetails entity.
     *
     * @param locationDetailsRequestDTO the LocationDetailsRequestDTO to convert
     * @return the converted LocationDetails entity
     */
    LocationDetails toEntity(LocationDetailsRequestDTO locationDetailsRequestDTO);

    /**
     * Converts LocationDetails entity to LocationDetailsResponseBasicDTO.
     */
    @Mapping(target = "manager", qualifiedByName = "basicEmployee")
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicLocationDetails")
    LocationDetailsResponseBasicDTO toBasicDTO(LocationDetails locationDetails);

    /**
     * Converts LocationDetails entity to LocationDetailsResponseDetailDTO.
     */
    @Mapping(target = "manager", qualifiedByName = "detailEmployee")
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailLocationDetails")
    LocationDetailsResponseDetailDTO toDetailDTO(LocationDetails locationDetails);
}
