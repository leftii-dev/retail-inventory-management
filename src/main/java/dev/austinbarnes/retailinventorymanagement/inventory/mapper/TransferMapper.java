package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    Transfer toEntity(TransferRequestDTO transferRequestDTO);
    TransferResponseBasicDTO toBasicDTO(Transfer transfer);
    TransferResponseDetailDTO toDetailDTO(Transfer transfer);
}
