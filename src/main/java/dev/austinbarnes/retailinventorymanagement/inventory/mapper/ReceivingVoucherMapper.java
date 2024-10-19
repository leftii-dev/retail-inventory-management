package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import dev.austinbarnes.retailinventorymanagement.location.mapper.LocationMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {PurchaseOrderMapper.class, LocationMapper.class, VendorMapper.class, StatusMapper.class})
public interface ReceivingVoucherMapper {
    ReceivingVoucher toEntity(ReceivingVoucherRequestDTO voucherRequestDTO);

    @Mapping(target = "purchaseOrder", qualifiedByName = "basicPurchaseOrder")
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Mapping(target = "vendor", qualifiedByName = "basicVendor")
    @Mapping(target = "status", qualifiedByName = "basicStatus")
    @Named("basicReceivingVoucher")
    ReceivingVoucherResponseBasicDTO toBasicDTO(ReceivingVoucher receivingVoucher);

    @Mapping(target = "purchaseOrder", qualifiedByName = "detailPurchaseOrder")
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Mapping(target = "vendor", qualifiedByName = "detailVendor")
    @Mapping(target = "status", qualifiedByName = "detailStatus")
    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(receivingVoucher.getCreatedBy().getNameLast() + \", \" + receivingVoucher.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(receivingVoucher.getModifiedBy().getNameLast() + \", \" + receivingVoucher.getModifiedBy().getNameFirst())")
    @Named("detailReceivingVoucher")
    ReceivingVoucherResponseDetailDTO toDetailDTO(ReceivingVoucher receivingVoucher);
}
