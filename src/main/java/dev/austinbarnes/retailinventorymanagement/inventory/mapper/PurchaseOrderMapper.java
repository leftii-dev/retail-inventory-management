package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {VendorMapper.class, StatusMapper.class})
public interface PurchaseOrderMapper {
    PurchaseOrder toEntity(PurchaseOrderRequestDTO purchaseOrderRequestDTO);

    @Mapping(target = "vendor", qualifiedByName = "basicVendor")
    @Mapping(target = "status", qualifiedByName = "basicStatus")
    @Named("basicPurchaseOrder")
    PurchaseOrderResponseBasicDTO toBasicDTO(PurchaseOrder purchaseOrder);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(purchaseOrder.getCreatedBy().getNameLast() + \", \" + purchaseOrder.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(purchaseOrder.getModifiedBy().getNameLast() + \", \" + purchaseOrder.getModifiedBy().getNameFirst())")
    @Mapping(target = "vendor", qualifiedByName = "detailVendor")
    @Mapping(target = "status", qualifiedByName = "detailStatus")
    @Named("detailPurchaseOrder")
    PurchaseOrderResponseDetailDTO toDetailDTO(PurchaseOrder purchaseOrder);
}
