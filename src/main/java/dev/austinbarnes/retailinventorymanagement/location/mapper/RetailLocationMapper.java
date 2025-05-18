package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.RetailLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * RetailLocationMapper is an interface that defines the mapping between RetailLocation entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert RetailLocationRequestDTO to RetailLocation entity and
 * to convert RetailLocation entity to different types of RetailLocationResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class})
public interface RetailLocationMapper {
    /**
     * Converts RetailLocationRequestDTO to RetailLocation entity.
     *
     * @param retailLocationRequestDTO the RetailLocationRequestDTO to convert
     * @return the converted RetailLocation entity
     */
    RetailLocation toEntity(RetailLocationRequestDTO retailLocationRequestDTO);

    /**
     * Converts RetailLocation entity to RetailLocationResponseBasicDTO.
     *
     * @param retailLocation the RetailLocation entity to convert
     * @return the converted RetailLocationResponseBasicDTO
     */
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicRetailLocation")
    RetailLocationResponseBasicDTO toBasicDTO(RetailLocation retailLocation);

    /**
     * Converts RetailLocation entity to RetailLocationResponseDetailDTO.
     *
     * @param retailLocation the RetailLocation entity to convert
     * @return the converted RetailLocationResponseDetailDTO
     */
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailRetailLocation")
    RetailLocationResponseDetailDTO toDetailDTO(RetailLocation retailLocation);
}
