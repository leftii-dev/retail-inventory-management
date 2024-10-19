package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {LocationTypeMapper.class})
public interface LocationMapper {
    Location toEntity(LocationRequestDTO locationRequestDTO);

    @Mapping(target =  "locationType", qualifiedByName = "basicLocationType")
    @Named("basicLocation")
    LocationResponseBasicDTO toBasicDTO(Location location);

    @Mapping(target =  "locationType", qualifiedByName = "detailLocationType")
    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(location.getCreatedBy().getNameLast() + \", \" + location.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(location.getModifiedBy().getNameLast() + \", \" + location.getModifiedBy().getNameFirst())")
    @Named("detailLocation")
    LocationResponseDetailDTO toDetailDTO(Location location);
}
