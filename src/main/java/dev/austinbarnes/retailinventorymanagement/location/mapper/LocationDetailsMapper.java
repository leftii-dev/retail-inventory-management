package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeMapper;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationDetails;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class, EmployeeMapper.class})
public interface LocationDetailsMapper {
    LocationDetails toEntity(LocationDetailsRequestDTO locationDetailsRequestDTO);

    @Mapping(target = "manager", qualifiedByName = "basicEmployee")
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicLocationDetails")
    LocationDetailsResponseBasicDTO toBasicDTO(LocationDetails locationDetails);

    @Mapping(target = "manager", qualifiedByName = "detailEmployee")
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(locationDetails.getCreatedBy().getNameLast() + \", \" + locationDetails.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(locationDetails.getModifiedBy().getNameLast() + \", \" + locationDetails.getModifiedBy().getNameFirst())")
    @Named("detailLocationDetails")
    LocationDetailsResponseDetailDTO toDetailDTO(LocationDetails locationDetails);
}
