package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationHours;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * LocationHoursMapper is an interface that defines the mapping between LocationHours entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert LocationHoursRequestDTO to LocationHours entity and
 * to convert LocationHours entity to different types of LocationHoursResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class})
public interface LocationHoursMapper {
    /**
     * Converts LocationHoursRequestDTO to LocationHours entity.
     *
     * @param locationHoursRequestDTO the LocationHoursRequestDTO to convert
     * @return the converted LocationHours entity
     */
    LocationHours toEntity(LocationHoursRequestDTO locationHoursRequestDTO);

    /**
     * Converts LocationHours entity to LocationHoursResponseBasicDTO.
     */
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicLocationHours")
    LocationHoursResponseBasicDTO toBasicDTO(LocationHours locationHours);

    /**
     * Converts LocationHours entity to LocationHoursResponseDetailDTO.
     */
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailLocationHours")
    LocationHoursResponseDetailDTO toDetailDTO(LocationHours locationHours);
}
