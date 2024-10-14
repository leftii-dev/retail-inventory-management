package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.details.LocationDetailsResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.LocationDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationDetailsMapper {
    LocationDetails toEntity(LocationDetailsRequestDTO locationDetailsRequestDTO);
    LocationDetailsResponseBasicDTO toBasicDTO(LocationDetails locationDetails);
    LocationDetailsResponseDetailDTO toDetailDTO(LocationDetails locationDetails);
}
