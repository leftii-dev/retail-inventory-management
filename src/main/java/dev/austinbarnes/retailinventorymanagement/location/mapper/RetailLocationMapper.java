package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.retail.RetailLocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.RetailLocation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailLocationMapper {
    RetailLocation toEntity(RetailLocationRequestDTO retailLocationRequestDTO);
    RetailLocationResponseBasicDTO toBasicDTO(RetailLocation retailLocation);
    RetailLocationResponseDetailDTO toDetailDTO(RetailLocation retailLocation);
}
