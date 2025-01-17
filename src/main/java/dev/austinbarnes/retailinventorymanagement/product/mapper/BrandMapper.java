package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface BrandMapper {
    Brand toEntity(BrandRequestDTO brandRequestDTO);

    @Named("basicBrand")
    BrandResponseBasicDTO toBasicDTO(Brand brand);

    @Named("detailBrand")
    BrandResponseDetailDTO toDetailDTO(Brand brand);
}
