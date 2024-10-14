package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationTypeMapper {
    LocationType toEntity(LocationTypeRequestDTO locationTypeRequestDTO);
    LocationTypeResponseBasicDTO toBasicDTO(LocationType locationType);
    LocationTypeResponseDetailDTO toDetailDTO(LocationType locationType);
}
