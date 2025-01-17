package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface StatusMapper {
    Status toEntity(StatusRequestDTO statusRequestDTO);

    @Named("basicStatus")
    StatusResponseBasicDTO toBasicDTO(Status status);

    @Named("detailStatus")
    StatusResponseDetailDTO toDetailDTO(Status status);
}
