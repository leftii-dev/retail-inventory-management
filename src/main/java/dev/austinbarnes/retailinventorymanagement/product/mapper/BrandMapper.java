package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toEntity(BrandRequestDTO brandRequestDTO);
    BrandResponseBasicDTO toBasic(Brand brand);
    BrandResponseDetailDTO toDetail(Brand brand);
}
