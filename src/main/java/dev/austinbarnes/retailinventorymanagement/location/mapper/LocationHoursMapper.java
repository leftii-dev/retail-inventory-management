package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationHours;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * LocationHoursMapper is an interface that defines the mapping between LocationHours entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert LocationHoursRequestDTO to LocationHours entity and
 * to convert LocationHours entity to different types of LocationHoursResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class})
public abstract class LocationHoursMapper {
    @Autowired
    protected LocationRepository locationRepository;
    /**
     * Converts LocationHoursRequestDTO to LocationHours entity.
     *
     * @param locationHoursRequestDTO the LocationHoursRequestDTO to convert
     * @return the converted LocationHours entity
     */
    @Mapping(target = "location", source = "locationID")
    public abstract LocationHours toEntity(LocationHoursRequestDTO locationHoursRequestDTO);

    /**
     * Converts LocationHours entity to LocationHoursResponseBasicDTO.
     */
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicLocationHours")
    public abstract LocationHoursResponseBasicDTO toBasicDTO(LocationHours locationHours);

    /**
     * Converts LocationHours entity to LocationHoursResponseDetailDTO.
     */
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailLocationHours")
    public abstract LocationHoursResponseDetailDTO toDetailDTO(LocationHours locationHours);

    /**
     * Updates an existing LocationHours entity with the values from the LocationHoursRequestDTO.
     *
     * @param locationHoursRequestDTO the LocationHoursRequestDTO containing the new values
     * @param locationHours           the LocationHours entity to update
     */
    @Mapping(target = "location", source = "locationID")
    public abstract void updateEntityFromRequest(LocationHoursRequestDTO locationHoursRequestDTO, @MappingTarget LocationHours locationHours);

    /**
     * Custom Resolver
     */
    protected Location resolveLocation(UUID locationID) {
        if(locationID == null) {
            return null;
        }
        return locationRepository.findById(locationID).orElse(null);
    }
}
