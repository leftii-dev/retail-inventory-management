package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * ReceivingVoucherItemMapper is an interface that defines the mapping between ReceivingVoucherItem entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert ReceivingVoucherItemRequestDTO to ReceivingVoucherItem entity and
 * to convert ReceivingVoucherItem entity to different types of ReceivingVoucherItemResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class})
public interface ReceivingVoucherItemMapper {
    /**
     * Converts ReceivingVoucherItemRequestDTO to ReceivingVoucherItem entity.
     *
     * @param receivingVoucherItemRequestDTO the ReceivingVoucherItemRequestDTO to convert
     * @return the converted ReceivingVoucherItem entity
     */
    ReceivingVoucherItem toEntity(ReceivingVoucherItemRequestDTO receivingVoucherItemRequestDTO);

    /**
     * Converts ReceivingVoucherItem entity to ReceivingVoucherItemResponseBasicDTO.
     *
     * @param receivingVoucherItem the ReceivingVoucherItem entity to convert
     * @return the converted ReceivingVoucherItemResponseBasicDTO
     */
    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Mapping(target = "receivingVoucherID", source = "receivingVoucher.id")
    @Named("basicReceivingVoucherItem")
    ReceivingVoucherItemResponseBasicDTO toBasicDTO(ReceivingVoucherItem receivingVoucherItem);

    /**
     * Converts ReceivingVoucherItem entity to ReceivingVoucherItemResponseDetailDTO.
     *
     * @param receivingVoucherItem the ReceivingVoucherItem entity to convert
     * @return the converted ReceivingVoucherItemResponseDetailDTO
     */
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Mapping(target = "receivingVoucherID", source = "receivingVoucher.id")
    @Named("detailReceivingVoucherItem")
    ReceivingVoucherItemResponseDetailDTO toDetailDTO(ReceivingVoucherItem receivingVoucherItem);
}
