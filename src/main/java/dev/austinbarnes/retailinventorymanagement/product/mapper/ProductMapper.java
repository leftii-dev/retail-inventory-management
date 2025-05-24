package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * ProductMapper is an interface that defines the mapping between the Product entity and its corresponding DTOs.
 * It uses MapStruct to generate the implementation at compile time.
 * The mappings include converting a ProductRequestDTO to a Product entity and converting a Product entity to
 * both a basic and detailed ProductResponseDTO.
 */
@Mapper(config = GlobalMapperConfig.class)
public interface ProductMapper {
    /**
     * Converts a ProductRequestDTO to a Product entity.
     *
     * @param productRequestDTO the ProductRequestDTO to convert
     * @return the converted Product entity
     */
    Product toEntity(ProductRequestDTO productRequestDTO);

    /**
     * Converts a Product entity to a ProductResponseBasicDTO.
     *
     * @param product the Product entity to convert
     * @return the converted ProductResponseBasicDTO
     */
    @Mapping(target = "categoryID", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandID", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "discountID", source = "discount.id")
    @Mapping(target = "discountName", source = "discount.name")
    @Named("basicProduct")
    ProductResponseBasicDTO toBasicDTO(Product product);

    /**
     * Converts a Product entity to a ProductResponseDetailDTO.
     *
     * @param product the Product entity to convert
     * @return the converted ProductResponseDetailDTO
     */
    @Mapping(target = "categoryID", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandID", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "discountID", source = "discount.id")
    @Mapping(target = "discountName", source = "discount.name")
    @Named("detailProduct")
    ProductResponseDetailDTO toDetailDTO(Product product);

    /**
     * Updates an existing Product entity with the values from the ProductRequestDTO.
     *
     * @param productRequestDTO the ProductRequestDTO containing the new values
     * @param product           the Product entity to update
     */
    void updateEntityFromRequest(ProductRequestDTO productRequestDTO, @MappingTarget Product product);
}
