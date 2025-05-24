package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * PurchaseOrderMapper is an interface that defines the mapping between PurchaseOrder entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert PurchaseOrderRequestDTO to PurchaseOrder entity and
 * to convert PurchaseOrder entity to different types of PurchaseOrderResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {VendorMapper.class, StatusMapper.class})
public interface PurchaseOrderMapper {
    /**
     * Converts PurchaseOrderRequestDTO to PurchaseOrder entity.
     *
     * @param purchaseOrderRequestDTO the PurchaseOrderRequestDTO to convert
     * @return the converted PurchaseOrder entity
     */
    PurchaseOrder toEntity(PurchaseOrderRequestDTO purchaseOrderRequestDTO);

    /**
     * Converts PurchaseOrder entity to PurchaseOrderResponseBasicDTO.
     *
     * @param purchaseOrder the PurchaseOrder entity to convert
     * @return the converted PurchaseOrderResponseBasicDTO
     */
    @Mapping(target = "vendor", qualifiedByName = "basicVendor")
    @Mapping(target = "status", qualifiedByName = "basicStatus")
    @Named("basicPurchaseOrder")
    PurchaseOrderResponseBasicDTO toBasicDTO(PurchaseOrder purchaseOrder);

    /**
     * Converts PurchaseOrder entity to PurchaseOrderResponseDetailDTO.
     *
     * @param purchaseOrder the PurchaseOrder entity to convert
     * @return the converted PurchaseOrderResponseDetailDTO
     */
    @Mapping(target = "vendor", qualifiedByName = "detailVendor")
    @Mapping(target = "status", qualifiedByName = "detailStatus")
    @Named("detailPurchaseOrder")
    PurchaseOrderResponseDetailDTO toDetailDTO(PurchaseOrder purchaseOrder);

    /**
     * Converts PurchaseOrder entity to PurchaseOrderResponseBasicDTO in-place.
     *
     * @param purchaseOrder the PurchaseOrder entity to convert
     */
    void updateEntityFromRequest(PurchaseOrderRequestDTO request, @MappingTarget PurchaseOrder purchaseOrder);
}
