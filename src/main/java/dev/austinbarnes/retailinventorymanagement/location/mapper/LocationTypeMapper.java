package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * LocationTypeMapper is an interface that defines the mapping between LocationType entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert LocationTypeRequestDTO to LocationType entity and
 * to convert LocationType entity to different types of LocationTypeResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class)
public interface LocationTypeMapper {
    /**
     * Converts LocationTypeRequestDTO to LocationType entity.
     *
     * @param locationTypeRequestDTO the LocationTypeRequestDTO to convert
     * @return the converted LocationType entity
     */
    LocationType toEntity(LocationTypeRequestDTO locationTypeRequestDTO);

    /**
     * Converts LocationType entity to LocationTypeResponseBasicDTO.
     */
    @Named("basicLocationType")
    LocationTypeResponseBasicDTO toBasicDTO(LocationType locationType);

    /**
     * Converts LocationType entity to LocationTypeResponseDetailDTO.
     */
    @Named("detailLocationType")
    LocationTypeResponseDetailDTO toDetailDTO(LocationType locationType);

    /**
     * Updates an existing LocationType entity with the values from the LocationTypeRequestDTO.
     *
     * @param locationTypeRequestDTO the LocationTypeRequestDTO containing the new values
     * @param locationType           the LocationType entity to update
     */
    void updateEntityFromRequest(LocationTypeRequestDTO locationTypeRequestDTO, @MappingTarget LocationType locationType);
}
