package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.CategoryHierarchy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface CategoryHierarchyMapper {
    CategoryHierarchy toEntity(CategoryHierarchyRequestDTO categoryHierarchyRequestDTO);

    @Mapping(target = "categoryID", source = "category.id")
    @Mapping(target = "parentCategoryID", source = "parentCategory.id")
    @Named("basicCategoryHierarchy")
    CategoryHierarchyResponseBasicDTO toBasicDTO(CategoryHierarchy categoryHierarchy);

    @Mapping(target = "categoryID", source = "category.id")
    @Mapping(target = "parentCategoryID", source = "parentCategory.id")
    @Named("detailCategoryHierarchy")
    CategoryHierarchyResponseDetailDTO toDetailDTO(CategoryHierarchy categoryHierarchy);
}
