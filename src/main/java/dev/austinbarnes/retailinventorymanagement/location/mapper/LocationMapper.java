package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * LocationMapper is an interface that defines the mapping between Location entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert LocationRequestDTO to Location entity and
 * to convert Location entity to different types of LocationResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {LocationTypeMapper.class})
public interface LocationMapper {
    /**
     * Converts LocationRequestDTO to Location entity.
     *
     * @param locationRequestDTO the LocationRequestDTO to convert
     * @return the converted Location entity
     */
    Location toEntity(LocationRequestDTO locationRequestDTO);

    /**
     * Converts Location entity to LocationResponseBasicDTO.
     *
     * @param location the Location entity to convert
     * @return the converted LocationResponseBasicDTO
     */
    @Mapping(target =  "locationType", qualifiedByName = "basicLocationType")
    @Named("basicLocation")
    LocationResponseBasicDTO toBasicDTO(Location location);

    /**
     * Converts Location entity to LocationResponseDetailDTO.
     *
     * @param location the Location entity to convert
     * @return the converted LocationResponseDetailDTO
     */
    @Mapping(target =  "locationType", qualifiedByName = "detailLocationType")
    @Named("detailLocation")
    LocationResponseDetailDTO toDetailDTO(Location location);

    /**
     * Updates an existing Location entity with the values from the LocationRequestDTO.
     *
     * @param locationRequestDTO the LocationRequestDTO containing the new values
     * @param location           the Location entity to update
     */
    void updateEntityFromRequest(LocationRequestDTO locationRequestDTO, @MappingTarget Location location);
}
