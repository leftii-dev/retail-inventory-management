package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.TransferItem;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.TransferRepository;
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
 * TransferItemMapper is an interface that defines the mapping between TransferItem entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert TransferItemRequestDTO to TransferItem entity and
 * to convert TransferItem entity to different types of TransferItemResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class})
public abstract class TransferItemMapper {
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected TransferRepository transferRepository;
    /**
     * Converts TransferItemRequestDTO to TransferItem entity.
     *
     * @param transferItemRequestDTO the TransferItemRequestDTO to convert
     * @return the converted TransferItem entity
     */
    @Mapping(target = "product", source = "productID")
    @Mapping(target = "transfer", source = "transferID")
    public abstract TransferItem toEntity(TransferItemRequestDTO transferItemRequestDTO);

    /**
     * Converts TransferItem entity to TransferItemResponseBasicDTO.
     *
     * @param transferItem the TransferItem entity to convert
     * @return the converted TransferItemResponseBasicDTO
     */
    @Mapping(target = "transferID", source = "transfer.id")
    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Named("basicTransferItem")
    public abstract TransferItemResponseBasicDTO toBasicDTO(TransferItem transferItem);

    /**
     * Converts TransferItem entity to TransferItemResponseDetailDTO.
     *
     * @param transferItem the TransferItem entity to convert
     * @return the converted TransferItemResponseDetailDTO
     */
    @Mapping(target = "transferID", source = "transfer.id")
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Named("detailTransferItem")
    public abstract TransferItemResponseDetailDTO toDetailDTO(TransferItem transferItem);

    /**
     * Updates an existing TransferItem entity with the values from the TransferItemRequestDTO.
     *
     * @param transferItemRequestDTO the TransferItemRequestDTO containing the new values
     * @param transferItem           the TransferItem entity to update
     */
    public abstract void updateEntityFromRequest(TransferItemRequestDTO transferItemRequestDTO, @MappingTarget TransferItem transferItem);

    /**
     * Custom Resolvers
     */
    protected Transfer resolveTransfer(UUID transferID) {
        return transferID == null ? null : transferRepository.findById(transferID).orElse(null);
    }

    protected Product resolveProduct(UUID productID) {
        return productID == null ? null : productRepository.findById(productID).orElse(null);
    }
}
