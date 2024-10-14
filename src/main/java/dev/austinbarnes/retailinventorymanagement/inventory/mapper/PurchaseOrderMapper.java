package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {
    PurchaseOrder toEntity(PurchaseOrderRequestDTO purchaseOrderRequestDTO);
    PurchaseOrderResponseBasicDTO toBasicDTO(PurchaseOrder purchaseOrder);
    PurchaseOrderResponseDetailDTO toDetailDTO(PurchaseOrder purchaseOrder);
}
