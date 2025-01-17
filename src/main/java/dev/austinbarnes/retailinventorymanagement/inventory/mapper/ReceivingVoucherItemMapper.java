package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
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

    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Mapping(target = "receivingVoucherID", source = "receivingVoucher.id")
    @Named("detailReceivingVoucherItem")
    ReceivingVoucherItemResponseDetailDTO toDetailDTO(ReceivingVoucherItem receivingVoucherItem);
}
