package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toEntity(LocationRequestDTO locationRequestDTO);
    LocationResponseBasicDTO toBasicDTO(Location location);
    LocationResponseDetailDTO toDetailDTO(Location location);
}
