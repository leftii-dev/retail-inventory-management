package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.RetailLocation;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
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
    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(retailLocation.getCreatedBy().getNameLast() + \", \" + retailLocation.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(retailLocation.getModifiedBy().getNameLast() + \", \" + retailLocation.getModifiedBy().getNameFirst())")
    @Named("detailRetailLocation")
    RetailLocationResponseDetailDTO toDetailDTO(RetailLocation retailLocation);
}
