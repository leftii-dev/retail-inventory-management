package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrderItem;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class})
public interface PurchaseOrderItemMapper {
    PurchaseOrderItem toEntity(PurchaseOrderItemRequestDTO purchaseOrderItemRequestDTO);

    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Mapping(target = "purchaseOrderID", source = "purchaseOrder.id")
    @Named("basicPurchaseOrderItem")
    PurchaseOrderItemResponseBasicDTO toBasicDTO(PurchaseOrderItem purchaseOrderItem);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(purchaseOrderItem.getCreatedBy().getNameLast() + \", \" + purchaseOrderItem.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(purchaseOrderItem.getModifiedBy().getNameLast() + \", \" + purchaseOrderItem.getModifiedBy().getNameFirst())")
    @Mapping(target = "purchaseOrderID", source = "purchaseOrder.id")
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Named("detailPurchaseOrderItem")
    PurchaseOrderItemResponseDetailDTO toDetailDTO(PurchaseOrderItem purchaseOrderItem);
}
