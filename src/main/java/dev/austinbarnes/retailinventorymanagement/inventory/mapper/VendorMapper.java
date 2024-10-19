package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface VendorMapper {
    Vendor toEntity(VendorRequestDTO vendorRequestDTO);

    @Named("basicVendor")
    VendorResponseBasicDTO toBasicDTO(Vendor vendor);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(vendor.getCreatedBy().getNameLast() + \", \" + vendor.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(vendor.getModifiedBy().getNameLast() + \", \" + vendor.getModifiedBy().getNameFirst())")
    @Named("detailVendor")
    VendorResponseDetailDTO toDetailDTO(Vendor vendor);
}
