package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.entity.RetailLocation;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * RetailLocationMapper is an interface that defines the mapping between RetailLocation entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert RetailLocationRequestDTO to RetailLocation entity and
 * to convert RetailLocation entity to different types of RetailLocationResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class})
public abstract class RetailLocationMapper {
    @Autowired
    protected LocationRepository locationRepository;
    /**
     * Converts RetailLocationRequestDTO to RetailLocation entity.
     *
     * @param retailLocationRequestDTO the RetailLocationRequestDTO to convert
     * @return the converted RetailLocation entity
     */
    @Mapping(target = "location", source = "locationID")
    public abstract RetailLocation toEntity(RetailLocationRequestDTO retailLocationRequestDTO);

    /**
     * Converts RetailLocation entity to RetailLocationResponseBasicDTO.
     *
     * @param retailLocation the RetailLocation entity to convert
     * @return the converted RetailLocationResponseBasicDTO
     */
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicRetailLocation")
    public abstract RetailLocationResponseBasicDTO toBasicDTO(RetailLocation retailLocation);

    /**
     * Converts RetailLocation entity to RetailLocationResponseDetailDTO.
     *
     * @param retailLocation the RetailLocation entity to convert
     * @return the converted RetailLocationResponseDetailDTO
     */
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailRetailLocation")
    public abstract RetailLocationResponseDetailDTO toDetailDTO(RetailLocation retailLocation);

    /**
     * Updates an existing RetailLocation entity with the values from the RetailLocationRequestDTO.
     *
     * @param retailLocationRequestDTO the RetailLocationRequestDTO containing the new values
     * @param retailLocation           the RetailLocation entity to update
     */
    @Mapping(target = "location", source = "locationID")
    public abstract void updateEntityFromRequest(RetailLocationRequestDTO retailLocationRequestDTO, @MappingTarget RetailLocation retailLocation);

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
