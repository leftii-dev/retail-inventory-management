package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.WarehouseLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * WarehouseLocationMapper is an interface that defines the mapping between WarehouseLocation entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert WarehouseLocationRequestDTO to WarehouseLocation entity and
 * to convert WarehouseLocation entity to different types of WarehouseLocationResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class})
public interface WarehouseLocationMapper {
    /**
     * Converts WarehouseLocationRequestDTO to WarehouseLocation entity.
     *
     * @param warehouseLocationRequestDTO the WarehouseLocationRequestDTO to convert
     * @return the converted WarehouseLocation entity
     */
    WarehouseLocation toEntity(WarehouseLocationRequestDTO warehouseLocationRequestDTO);

    /**
     * Converts WarehouseLocation entity to WarehouseLocationResponseBasicDTO.
     *
     * @param warehouseLocation the WarehouseLocation entity to convert
     * @return the converted WarehouseLocationResponseBasicDTO
     */
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicWarehouseLocation")
    WarehouseLocationResponseBasicDTO toBasicDTO(WarehouseLocation warehouseLocation);

    /**
     * Converts WarehouseLocation entity to WarehouseLocationResponseDetailDTO.
     *
     * @param warehouseLocation the WarehouseLocation entity to convert
     * @return the converted WarehouseLocationResponseDetailDTO
     */
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailWarehouseLocation")
    WarehouseLocationResponseDetailDTO toDetailDTO(WarehouseLocation warehouseLocation);
}
