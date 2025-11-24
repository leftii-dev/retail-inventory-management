package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.purchaseorder.PurchaseOrderItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrderItem;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.PurchaseOrderRepository;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import dev.austinbarnes.retailinventorymanagement.product.repo.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * PurchaseOrderItemMapper is an interface that defines the mapping between PurchaseOrderItem entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert PurchaseOrderItemRequestDTO to PurchaseOrderItem entity and
 * to convert PurchaseOrderItem entity to different types of PurchaseOrderItemResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class})
public abstract class PurchaseOrderItemMapper {
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected PurchaseOrderRepository purchaseOrderRepository;
    /**
     * Converts PurchaseOrderItemRequestDTO to PurchaseOrderItem entity.
     *
     * @param purchaseOrderItemRequestDTO the PurchaseOrderItemRequestDTO to convert
     * @return the converted PurchaseOrderItem entity
     */
    @Mapping(target = "purchaseOrder", source = "purchaseOrderID")
    @Mapping(target = "product", source = "productID")
    public abstract PurchaseOrderItem toEntity(PurchaseOrderItemRequestDTO purchaseOrderItemRequestDTO);

    /**
     * Converts PurchaseOrderItem entity to PurchaseOrderItemResponseBasicDTO.
     *
     * @param purchaseOrderItem the PurchaseOrderItem entity to convert
     * @return the converted PurchaseOrderItemResponseBasicDTO
     */
    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Mapping(target = "purchaseOrderID", source = "purchaseOrder.id")
    @Named("basicPurchaseOrderItem")
    public abstract PurchaseOrderItemResponseBasicDTO toBasicDTO(PurchaseOrderItem purchaseOrderItem);

    /**
     * Converts PurchaseOrderItem entity to PurchaseOrderItemResponseDetailDTO.
     *
     * @param purchaseOrderItem the PurchaseOrderItem entity to convert
     * @return the converted PurchaseOrderItemResponseDetailDTO
     */
    @Mapping(target = "purchaseOrderID", source = "purchaseOrder.id")
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Named("detailPurchaseOrderItem")
    public abstract PurchaseOrderItemResponseDetailDTO toDetailDTO(PurchaseOrderItem purchaseOrderItem);

    /**
     * Updates an existing PurchaseOrderItem entity with the values from the PurchaseOrderItemRequestDTO.
     *
     * @param purchaseOrderItemRequestDTO the PurchaseOrderItemRequestDTO containing the new values
     * @param purchaseOrderItem           the PurchaseOrderItem entity to update
     */
    @Mapping(target = "purchaseOrder", source = "purchaseOrderID")
    @Mapping(target = "product", source = "productID")
    public abstract void updateEntityFromRequest(PurchaseOrderItemRequestDTO purchaseOrderItemRequestDTO, @MappingTarget PurchaseOrderItem purchaseOrderItem);

    /**
     * Custom Resolvers
     */
    protected PurchaseOrder resolvePurchaseOrder(UUID id) {
        return id == null ? null : purchaseOrderRepository.findById(id).orElse(null);
    }

    protected Product resolveProduct(UUID id) {
        return id == null ? null : productRepository.findById(id).orElse(null);
    }
}
