package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseDTO;
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

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(brand.getCreatedBy().getNameLast() + \", \" + brand.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(brand.getModifiedBy().getNameLast() + \", \" + brand.getModifiedBy().getNameFirst())")
    @Named("detailBrand")
    BrandResponseDetailDTO toDetailDTO(Brand brand);
}
