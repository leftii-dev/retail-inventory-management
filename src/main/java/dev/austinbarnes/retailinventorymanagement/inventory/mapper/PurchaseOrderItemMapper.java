package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrderItem;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * PurchaseOrderItemMapper is an interface that defines the mapping between PurchaseOrderItem entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert PurchaseOrderItemRequestDTO to PurchaseOrderItem entity and
 * to convert PurchaseOrderItem entity to different types of PurchaseOrderItemResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class})
public interface PurchaseOrderItemMapper {
    /**
     * Converts PurchaseOrderItemRequestDTO to PurchaseOrderItem entity.
     *
     * @param purchaseOrderItemRequestDTO the PurchaseOrderItemRequestDTO to convert
     * @return the converted PurchaseOrderItem entity
     */
    PurchaseOrderItem toEntity(PurchaseOrderItemRequestDTO purchaseOrderItemRequestDTO);

    /**
     * Converts PurchaseOrderItem entity to PurchaseOrderItemResponseBasicDTO.
     *
     * @param purchaseOrderItem the PurchaseOrderItem entity to convert
     * @return the converted PurchaseOrderItemResponseBasicDTO
     */
    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Mapping(target = "purchaseOrderID", source = "purchaseOrder.id")
    @Named("basicPurchaseOrderItem")
    PurchaseOrderItemResponseBasicDTO toBasicDTO(PurchaseOrderItem purchaseOrderItem);

    /**
     * Converts PurchaseOrderItem entity to PurchaseOrderItemResponseDetailDTO.
     *
     * @param purchaseOrderItem the PurchaseOrderItem entity to convert
     * @return the converted PurchaseOrderItemResponseDetailDTO
     */
    @Mapping(target = "purchaseOrderID", source = "purchaseOrder.id")
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Named("detailPurchaseOrderItem")
    PurchaseOrderItemResponseDetailDTO toDetailDTO(PurchaseOrderItem purchaseOrderItem);
}
