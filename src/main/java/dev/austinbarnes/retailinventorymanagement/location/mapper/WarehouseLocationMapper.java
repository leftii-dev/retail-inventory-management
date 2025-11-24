package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.entity.WarehouseLocation;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * WarehouseLocationMapper is an interface that defines the mapping between WarehouseLocation entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert WarehouseLocationRequestDTO to WarehouseLocation entity and
 * to convert WarehouseLocation entity to different types of WarehouseLocationResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class})
public abstract class WarehouseLocationMapper {
    @Autowired
    protected LocationRepository locationRepository;

    /**
     * Converts WarehouseLocationRequestDTO to WarehouseLocation entity.
     *
     * @param warehouseLocationRequestDTO the WarehouseLocationRequestDTO to convert
     * @return the converted WarehouseLocation entity
     */
    @Mapping(target = "location", source = "locationID")
    public abstract WarehouseLocation toEntity(WarehouseLocationRequestDTO warehouseLocationRequestDTO);

    /**
     * Converts WarehouseLocation entity to WarehouseLocationResponseBasicDTO.
     *
     * @param warehouseLocation the WarehouseLocation entity to convert
     * @return the converted WarehouseLocationResponseBasicDTO
     */
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicWarehouseLocation")
    public abstract WarehouseLocationResponseBasicDTO toBasicDTO(WarehouseLocation warehouseLocation);

    /**
     * Converts WarehouseLocation entity to WarehouseLocationResponseDetailDTO.
     *
     * @param warehouseLocation the WarehouseLocation entity to convert
     * @return the converted WarehouseLocationResponseDetailDTO
     */
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailWarehouseLocation")
    public abstract WarehouseLocationResponseDetailDTO toDetailDTO(WarehouseLocation warehouseLocation);

    /**
     * Updates an existing WarehouseLocation entity with the values from the WarehouseLocationRequestDTO.
     *
     * @param warehouseLocationRequestDTO the WarehouseLocationRequestDTO containing the new values
     * @param warehouseLocation           the WarehouseLocation entity to update
     */
    public abstract void updateEntityFromRequest(WarehouseLocationRequestDTO warehouseLocationRequestDTO, @MappingTarget WarehouseLocation warehouseLocation);

    /**
     * Custom Resolver
     */
    protected Location resolveLocation(UUID locationID) {
        if (locationID == null) {
            return null;
        }
        return locationRepository.findById(locationID).orElse(null);
    }
}
