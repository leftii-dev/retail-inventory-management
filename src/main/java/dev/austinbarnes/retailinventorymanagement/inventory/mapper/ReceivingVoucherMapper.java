package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import dev.austinbarnes.retailinventorymanagement.location.mapper.LocationMapper;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * ReceivingVoucherMapper is an interface that defines the mapping between ReceivingVoucher entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert ReceivingVoucherRequestDTO to ReceivingVoucher entity and
 * to convert ReceivingVoucher entity to different types of ReceivingVoucherResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {PurchaseOrderMapper.class, LocationMapper.class, VendorMapper.class, StatusMapper.class})
public interface ReceivingVoucherMapper {
    /**
     * Converts ReceivingVoucherRequestDTO to ReceivingVoucher entity.
     *
     * @param voucherRequestDTO the ReceivingVoucherRequestDTO to convert
     * @return the converted ReceivingVoucher entity
     */
    ReceivingVoucher toEntity(ReceivingVoucherRequestDTO voucherRequestDTO);

    /**
     * Converts ReceivingVoucher entity to ReceivingVoucherResponseBasicDTO.
     *
     * @param receivingVoucher the ReceivingVoucher entity to convert
     * @return the converted ReceivingVoucherResponseBasicDTO
     */
    @Mapping(target = "purchaseOrder", qualifiedByName = "basicPurchaseOrder")
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Mapping(target = "vendor", qualifiedByName = "basicVendor")
    @Mapping(target = "status", qualifiedByName = "basicStatus")
    @Named("basicReceivingVoucher")
    ReceivingVoucherResponseBasicDTO toBasicDTO(ReceivingVoucher receivingVoucher);

    /**
     * Converts ReceivingVoucher entity to ReceivingVoucherResponseDetailDTO.
     *
     * @param receivingVoucher the ReceivingVoucher entity to convert
     * @return the converted ReceivingVoucherResponseDetailDTO
     */
    @Mapping(target = "purchaseOrder", qualifiedByName = "detailPurchaseOrder")
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Mapping(target = "vendor", qualifiedByName = "detailVendor")
    @Mapping(target = "status", qualifiedByName = "detailStatus")
    @Named("detailReceivingVoucher")
    ReceivingVoucherResponseDetailDTO toDetailDTO(ReceivingVoucher receivingVoucher);
}
