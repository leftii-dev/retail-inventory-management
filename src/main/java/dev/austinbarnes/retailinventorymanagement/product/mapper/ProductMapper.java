package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface ProductMapper {
    Product toEntity(ProductRequestDTO productRequestDTO);

    @Mapping(target = "categoryID", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandID", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "discountID", source = "discount.id")
    @Mapping(target = "discountName", source = "discount.name")
    @Named("basicProduct")
    ProductResponseBasicDTO toBasicDTO(Product product);

    @Mapping(target = "categoryID", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandID", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "discountID", source = "discount.id")
    @Mapping(target = "discountName", source = "discount.name")
    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(product.getCreatedBy().getNameLast() + \", \" + product.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(product.getModifiedBy().getNameLast() + \", \" + product.getModifiedBy().getNameFirst())")
    @Named("detailProduct")
    ProductResponseDetailDTO toDetailDTO(Product product);
}
