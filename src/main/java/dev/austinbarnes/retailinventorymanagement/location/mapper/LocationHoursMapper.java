package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationHours;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class})
public interface LocationHoursMapper {
    LocationHours toEntity(LocationHoursRequestDTO locationHoursRequestDTO);

    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicLocationHours")
    LocationHoursResponseBasicDTO toBasicDTO(LocationHours locationHours);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(locationHours.getCreatedBy().getNameLast() + \", \" + locationHours.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(locationHours.getModifiedBy().getNameLast() + \", \" + locationHours.getModifiedBy().getNameFirst())")
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailLocationHours")
    LocationHoursResponseDetailDTO toDetailDTO(LocationHours locationHours);
}
