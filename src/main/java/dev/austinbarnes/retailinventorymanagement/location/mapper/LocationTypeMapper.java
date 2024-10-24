package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationType;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface LocationTypeMapper {
    LocationType toEntity(LocationTypeRequestDTO locationTypeRequestDTO);

    @Named("basicLocationType")
    LocationTypeResponseBasicDTO toBasicDTO(LocationType locationType);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(locationType.getCreatedBy().getNameLast() + \", \" + locationType.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(locationType.getModifiedBy().getNameLast() + \", \" + locationType.getModifiedBy().getNameFirst())")
    @Named("detailLocationType")
    LocationTypeResponseDetailDTO toDetailDTO(LocationType locationType);
}
