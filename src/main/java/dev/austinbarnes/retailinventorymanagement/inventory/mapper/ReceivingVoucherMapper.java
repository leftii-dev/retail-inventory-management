package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.receivingvoucher.ReceivingVoucherResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.PurchaseOrder;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.ReceivingVoucher;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.PurchaseOrderRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.StatusRepository;
import dev.austinbarnes.retailinventorymanagement.inventory.repo.VendorRepository;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.mapper.LocationMapper;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * ReceivingVoucherMapper is an interface that defines the mapping between ReceivingVoucher entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert ReceivingVoucherRequestDTO to ReceivingVoucher entity and
 * to convert ReceivingVoucher entity to different types of ReceivingVoucherResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {PurchaseOrderMapper.class, LocationMapper.class, VendorMapper.class, StatusMapper.class})
public abstract class ReceivingVoucherMapper {
    @Autowired
    protected StatusRepository statusRepository;
    @Autowired
    protected LocationRepository locationRepository;
    @Autowired
    protected VendorRepository vendorRepository;
    @Autowired
    protected PurchaseOrderRepository purchaseOrderRepository;
    /**
     * Converts ReceivingVoucherRequestDTO to ReceivingVoucher entity.
     *
     * @param voucherRequestDTO the ReceivingVoucherRequestDTO to convert
     * @return the converted ReceivingVoucher entity
     */
    @Mapping(target = "status", source = "statusID")
    @Mapping(target = "location", source = "locationID")
    @Mapping(target = "vendor", source = "vendorID")
    @Mapping(target = "purchaseOrder", source = "purchaseOrderID")
    public abstract ReceivingVoucher toEntity(ReceivingVoucherRequestDTO voucherRequestDTO);

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
    public abstract ReceivingVoucherResponseBasicDTO toBasicDTO(ReceivingVoucher receivingVoucher);

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
    public abstract ReceivingVoucherResponseDetailDTO toDetailDTO(ReceivingVoucher receivingVoucher);

    /**
     * Updates an existing ReceivingVoucher entity with the values from the ReceivingVoucherRequestDTO.
     *
     * @param voucherRequestDTO the ReceivingVoucherRequestDTO containing the new values
     * @param receivingVoucher   the ReceivingVoucher entity to update
     */
    @Mapping(target = "status", source = "statusID")
    @Mapping(target = "location", source = "locationID")
    @Mapping(target = "vendor", source = "vendorID")
    @Mapping(target = "purchaseOrder", source = "purchaseOrderID")
    public abstract void updateEntityFromRequest(ReceivingVoucherRequestDTO voucherRequestDTO, @MappingTarget ReceivingVoucher receivingVoucher);

    /**
     * Custom Resolvers
     */
    protected Location resolveLocation(UUID locationID) {
        return locationID == null ? null : locationRepository.findById(locationID).orElse(null);
    }

    protected PurchaseOrder resolvePurchaseOrder(UUID purchaseOrderID) {
        return purchaseOrderID == null ? null : purchaseOrderRepository.findById(purchaseOrderID).orElse(null);
    }
}
