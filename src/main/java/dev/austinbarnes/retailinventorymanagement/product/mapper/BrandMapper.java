package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.brand.BrandResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * BrandMapper is an interface that defines the mapping between the Brand entity and its corresponding DTOs.
 * It uses MapStruct to generate the implementation at compile time.
 * The mappings include converting a BrandRequestDTO to a Brand entity and converting a Brand entity to
 * both a basic and detailed BrandResponseDTO.
 */
@Mapper(config = GlobalMapperConfig.class)
public interface BrandMapper {
    /**
     * Converts a BrandRequestDTO to a Brand entity.
     *
     * @param brandRequestDTO the BrandRequestDTO to convert
     * @return the converted Brand entity
     */
    Brand toEntity(BrandRequestDTO brandRequestDTO);

    /**
     * Converts a Brand entity to a BrandResponseBasicDTO.
     *
     * @param brand the Brand entity to convert
     * @return the converted BrandResponseBasicDTO
     */
    @Named("basicBrand")
    BrandResponseBasicDTO toBasicDTO(Brand brand);

    /**
     * Converts a Brand entity to a BrandResponseDetailDTO.
     *
     * @param brand the Brand entity to convert
     * @return the converted BrandResponseDetailDTO
     */
    @Named("detailBrand")
    BrandResponseDetailDTO toDetailDTO(Brand brand);

    /**
     * Updates an existing Brand entity with the values from the BrandRequestDTO.
     *
     * @param brandRequestDTO the BrandRequestDTO containing the new values
     * @param brand           the Brand entity to update
     */
    void updateEntityFromRequest(BrandRequestDTO brandRequestDTO, @MappingTarget Brand brand);
}
