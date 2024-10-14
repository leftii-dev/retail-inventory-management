package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    Status toEntity(StatusRequestDTO statusRequestDTO);
    StatusResponseBasicDTO toBasicDTO(Status status);
    StatusResponseDetailDTO toDetailDTO(Status status);
}
