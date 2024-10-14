package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.vendor.VendorResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Vendor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    Vendor toEntity(VendorRequestDTO vendorRequestDTO);
    VendorResponseBasicDTO toBasicDTO(Vendor vendor);
    VendorResponseDetailDTO toDetailDTO(Vendor vendor);
}
