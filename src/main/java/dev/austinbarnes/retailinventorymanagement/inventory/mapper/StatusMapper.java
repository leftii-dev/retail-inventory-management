package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface StatusMapper {
    Status toEntity(StatusRequestDTO statusRequestDTO);

    @Named("basicStatus")
    StatusResponseBasicDTO toBasicDTO(Status status);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(status.getCreatedBy().getNameLast() + \", \" + status.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(status.getModifiedBy().getNameLast() + \", \" + status.getModifiedBy().getNameFirst())")
    @Named("detailStatus")
    StatusResponseDetailDTO toDetailDTO(Status status);
}
