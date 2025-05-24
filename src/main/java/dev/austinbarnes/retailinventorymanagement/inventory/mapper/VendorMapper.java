package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * VendorMapper is an interface that defines the mapping between Vendor entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert VendorRequestDTO to Vendor entity and
 * to convert Vendor entity to different types of VendorResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class)
public interface VendorMapper {
    /**
     * Converts VendorRequestDTO to Vendor entity.
     *
     * @param vendorRequestDTO the VendorRequestDTO to convert
     * @return the converted Vendor entity
     */
    Vendor toEntity(VendorRequestDTO vendorRequestDTO);

    /**
     * Converts Vendor entity to VendorResponseBasicDTO.
     *
     * @param vendor the Vendor entity to convert
     * @return the converted VendorResponseBasicDTO
     */
    @Named("basicVendor")
    VendorResponseBasicDTO toBasicDTO(Vendor vendor);

    /**
     * Converts Vendor entity to VendorResponseDetailDTO.
     *
     * @param vendor the Vendor entity to convert
     * @return the converted VendorResponseDetailDTO
     */
    @Named("detailVendor")
    VendorResponseDetailDTO toDetailDTO(Vendor vendor);

    /**
     * Updates an existing Vendor entity with the values from the VendorRequestDTO.
     *
     * @param vendorRequestDTO the VendorRequestDTO containing the new values
     * @param vendor           the Vendor entity to update
     */
    void updateEntityFromRequest(VendorRequestDTO vendorRequestDTO, @MappingTarget Vendor vendor);
}
