package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class})
public interface ReceivingVoucherItemMapper {
    ReceivingVoucherItem toEntity(ReceivingVoucherItemRequestDTO receivingVoucherItemRequestDTO);

    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Mapping(target = "receivingVoucherID", source = "receivingVoucher.id")
    @Named("basicReceivingVoucherItem")
    ReceivingVoucherItemResponseBasicDTO toBasicDTO(ReceivingVoucherItem receivingVoucherItem);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(receivingVoucherItem.getCreatedBy().getNameLast() + \", \" + receivingVoucherItem.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(receivingVoucherItem.getModifiedBy().getNameLast() + \", \" + receivingVoucherItem.getModifiedBy().getNameFirst())")
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Mapping(target = "receivingVoucherID", source = "receivingVoucher.id")
    @Named("detailReceivingVoucherItem")
    ReceivingVoucherItemResponseDetailDTO toDetailDTO(ReceivingVoucherItem receivingVoucherItem);
}
