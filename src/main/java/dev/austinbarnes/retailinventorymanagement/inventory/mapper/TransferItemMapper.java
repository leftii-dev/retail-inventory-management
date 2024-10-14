package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.TransferItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferItemMapper {
    TransferItem toEntity(TransferItemRequestDTO transferItemRequestDTO);
    TransferItemResponseBasicDTO toBasicDTO(TransferItem transferItem);
    TransferItemResponseDetailDTO toDetailDTO(TransferItem transferItem);
}
