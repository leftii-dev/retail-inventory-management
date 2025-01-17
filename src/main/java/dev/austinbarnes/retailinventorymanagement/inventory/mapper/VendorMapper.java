package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface VendorMapper {
    Vendor toEntity(VendorRequestDTO vendorRequestDTO);

    @Named("basicVendor")
    VendorResponseBasicDTO toBasicDTO(Vendor vendor);

    @Named("detailVendor")
    VendorResponseDetailDTO toDetailDTO(Vendor vendor);
}
