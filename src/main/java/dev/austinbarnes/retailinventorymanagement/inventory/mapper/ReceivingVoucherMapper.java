package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceivingVoucherMapper {
    ReceivingVoucher toEntity(ReceivingVoucherRequestDTO voucherRequestDTO);
    ReceivingVoucherResponseBasicDTO toBasicDTO(ReceivingVoucher receivingVoucher);
    ReceivingVoucherResponseDetailDTO toDetailDTO(ReceivingVoucher receivingVoucher);
}
