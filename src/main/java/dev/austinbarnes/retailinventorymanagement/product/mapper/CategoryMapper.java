package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface CategoryMapper {
    Category toEntity(CategoryRequestDTO categoryRequestDTO);

    @Mapping(target = "discountID", source = "discount.id")
    @Named("basicCategory")
    CategoryResponseBasicDTO toBasicDTO(Category category);

    @Mapping(target = "discountID", source = "discount.id")
    @Named("detailCategory")
    CategoryResponseDetailDTO toDetailDTO(Category category);
}
