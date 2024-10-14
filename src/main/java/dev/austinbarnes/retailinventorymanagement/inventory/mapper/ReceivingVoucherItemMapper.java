package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceivingVoucherItemMapper {
    ReceivingVoucherItem toEntity(ReceivingVoucherItemRequestDTO receivingVoucherItemRequestDTO);
    ReceivingVoucherItemResponseBasicDTO toBasicDTO(ReceivingVoucherItem receivingVoucherItem);
    ReceivingVoucherItemResponseDetailDTO toDetailDTO(ReceivingVoucherItem receivingVoucherItem);
}
