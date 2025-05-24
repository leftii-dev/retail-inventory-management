package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.TransferItem;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * TransferItemMapper is an interface that defines the mapping between TransferItem entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert TransferItemRequestDTO to TransferItem entity and
 * to convert TransferItem entity to different types of TransferItemResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class})
public interface TransferItemMapper {
    /**
     * Converts TransferItemRequestDTO to TransferItem entity.
     *
     * @param transferItemRequestDTO the TransferItemRequestDTO to convert
     * @return the converted TransferItem entity
     */
    TransferItem toEntity(TransferItemRequestDTO transferItemRequestDTO);

    /**
     * Converts TransferItem entity to TransferItemResponseBasicDTO.
     *
     * @param transferItem the TransferItem entity to convert
     * @return the converted TransferItemResponseBasicDTO
     */
    @Mapping(target = "transferID", source = "transfer.id")
    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Named("basicTransferItem")
    TransferItemResponseBasicDTO toBasicDTO(TransferItem transferItem);

    /**
     * Converts TransferItem entity to TransferItemResponseDetailDTO.
     *
     * @param transferItem the TransferItem entity to convert
     * @return the converted TransferItemResponseDetailDTO
     */
    @Mapping(target = "transferID", source = "transfer.id")
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Named("detailTransferItem")
    TransferItemResponseDetailDTO toDetailDTO(TransferItem transferItem);

    /**
     * Updates an existing TransferItem entity with the values from the TransferItemRequestDTO.
     *
     * @param transferItemRequestDTO the TransferItemRequestDTO containing the new values
     * @param transferItem           the TransferItem entity to update
     */
    void updateEntityFromRequest(TransferItemRequestDTO transferItemRequestDTO, @MappingTarget TransferItem transferItem);
}
