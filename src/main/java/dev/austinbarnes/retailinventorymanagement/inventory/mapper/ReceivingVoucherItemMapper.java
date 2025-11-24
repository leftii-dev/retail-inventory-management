package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherItemResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucherItem;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.ReceivingVoucherRepository;
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
 * ReceivingVoucherItemMapper is an interface that defines the mapping between ReceivingVoucherItem entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert ReceivingVoucherItemRequestDTO to ReceivingVoucherItem entity and
 * to convert ReceivingVoucherItem entity to different types of ReceivingVoucherItemResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class})
public abstract class ReceivingVoucherItemMapper {
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected ReceivingVoucherRepository receivingVoucherRepository;
    /**
     * Converts ReceivingVoucherItemRequestDTO to ReceivingVoucherItem entity.
     *
     * @param receivingVoucherItemRequestDTO the ReceivingVoucherItemRequestDTO to convert
     * @return the converted ReceivingVoucherItem entity
     */
    @Mapping(target = "product", source = "productID")
    @Mapping(target = "receivingVoucher", source = "receivingVoucherID")
    public abstract ReceivingVoucherItem toEntity(ReceivingVoucherItemRequestDTO receivingVoucherItemRequestDTO);

    /**
     * Converts ReceivingVoucherItem entity to ReceivingVoucherItemResponseBasicDTO.
     *
     * @param receivingVoucherItem the ReceivingVoucherItem entity to convert
     * @return the converted ReceivingVoucherItemResponseBasicDTO
     */
    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Mapping(target = "receivingVoucherID", source = "receivingVoucher.id")
    @Named("basicReceivingVoucherItem")
    public abstract ReceivingVoucherItemResponseBasicDTO toBasicDTO(ReceivingVoucherItem receivingVoucherItem);

    /**
     * Converts ReceivingVoucherItem entity to ReceivingVoucherItemResponseDetailDTO.
     *
     * @param receivingVoucherItem the ReceivingVoucherItem entity to convert
     * @return the converted ReceivingVoucherItemResponseDetailDTO
     */
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Mapping(target = "receivingVoucherID", source = "receivingVoucher.id")
    @Named("detailReceivingVoucherItem")
    public abstract ReceivingVoucherItemResponseDetailDTO toDetailDTO(ReceivingVoucherItem receivingVoucherItem);

    /**
     * Updates an existing ReceivingVoucherItem entity with the values from the ReceivingVoucherItemRequestDTO.
     *
     * @param receivingVoucherItemRequestDTO the ReceivingVoucherItemRequestDTO containing the new values
     * @param receivingVoucherItem           the ReceivingVoucherItem entity to update
     */
    @Mapping(target = "product", source = "productID")
    @Mapping(target = "receivingVoucher", source = "receivingVoucherID")
    public abstract void updateEntityFromRequest(ReceivingVoucherItemRequestDTO receivingVoucherItemRequestDTO, @MappingTarget ReceivingVoucherItem receivingVoucherItem);

    /**
     * Custom Resolvers
     */
    protected Product resolveProduct(UUID id){
        return id == null ? null : productRepository.findById(id).orElse(null);
    }

    protected ReceivingVoucher resolveReceivingVoucher(UUID id){
        return id == null ? null : receivingVoucherRepository.findById(id).orElse(null);
    }
}
