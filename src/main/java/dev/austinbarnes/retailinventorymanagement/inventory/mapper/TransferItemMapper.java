package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.TransferItem;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class})
public interface TransferItemMapper {
    TransferItem toEntity(TransferItemRequestDTO transferItemRequestDTO);

    @Mapping(target = "transferID", source = "transfer.id")
    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Named("basicTransferItem")
    TransferItemResponseBasicDTO toBasicDTO(TransferItem transferItem);

    @Mapping(target = "transferID", source = "transfer.id")
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(transferItem.getCreatedBy().getNameLast() + \", \" + transferItem.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(transferItem.getModifiedBy().getNameLast() + \", \" + transferItem.getModifiedBy().getNameFirst())")
    @Named("detailTransferItem")
    TransferItemResponseDetailDTO toDetailDTO(TransferItem transferItem);
}
