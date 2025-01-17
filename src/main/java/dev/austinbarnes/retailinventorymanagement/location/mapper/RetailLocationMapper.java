package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.RetailLocation;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class})
public interface RetailLocationMapper {
    RetailLocation toEntity(RetailLocationRequestDTO retailLocationRequestDTO);

    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicRetailLocation")
    RetailLocationResponseBasicDTO toBasicDTO(RetailLocation retailLocation);

    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailRetailLocation")
    RetailLocationResponseDetailDTO toDetailDTO(RetailLocation retailLocation);
}
