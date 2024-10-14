package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseBasicDTO toBasicDTO(Category category);
    CategoryResponseDetailDTO toDetailDTO(Category category);
}
