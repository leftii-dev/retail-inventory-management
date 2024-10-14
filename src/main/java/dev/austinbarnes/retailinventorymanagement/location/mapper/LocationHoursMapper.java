package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.hours.LocationHoursResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationHours;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationHoursMapper {
    LocationHours toEntity(LocationHoursRequestDTO locationHoursRequestDTO);
    LocationHoursResponseBasicDTO toBasicDTO(LocationHours locationHours);
    LocationHoursResponseDetailDTO toDetailDTO(LocationHours locationHours);
}
