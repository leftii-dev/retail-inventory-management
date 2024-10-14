package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderItemMapper {
    PurchaseOrderItem toEntity(PurchaseOrderItemRequestDTO purchaseOrderItemRequestDTO);
    PurchaseOrderItemResponseBasicDTO toBasicDTO(PurchaseOrderItem purchaseOrderItem);
    PurchaseOrderItemResponseDetailDTO toDetailDTO(PurchaseOrderItem purchaseOrderItem);
}
