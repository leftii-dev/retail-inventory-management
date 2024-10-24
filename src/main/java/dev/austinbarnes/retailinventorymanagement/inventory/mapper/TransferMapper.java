package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface TransferMapper {

    // TODO: Once repos and services are created, add LocationService to `uses` and inject to retrieve locationTo/From
    @Mapping(target = "locationTo", ignore = true) // Remove after injection complete
    @Mapping(target = "locationFrom", ignore = true) // Remove after injection complete
    Transfer toEntity(TransferRequestDTO transferRequestDTO);

    @Mapping(target = "locationToID", source = "locationTo.id")
    @Mapping(target = "locationFromID", source = "locationFrom.id")
    @Named("basicTransfer")
    TransferResponseBasicDTO toBasicDTO(Transfer transfer);

    @Mapping(target = "locationToID", source = "locationTo.id")
    @Mapping(target = "locationFromID", source = "locationFrom.id")
    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(transfer.getCreatedBy().getNameLast() + \", \" + transfer.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(transfer.getModifiedBy().getNameLast() + \", \" + transfer.getModifiedBy().getNameFirst())")
    @Named("detailTransfer")
    TransferResponseDetailDTO toDetailDTO(Transfer transfer);
}
