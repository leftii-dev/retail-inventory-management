package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.CategoryHierarchy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * CategoryHierarchyMapper is an interface that defines the mapping between the CategoryHierarchy entity and its corresponding DTOs.
 * It uses MapStruct to generate the implementation at compile time.
 * The mappings include converting a CategoryHierarchyRequestDTO to a CategoryHierarchy entity and converting a CategoryHierarchy entity to
 * both a basic and detailed CategoryHierarchyResponseDTO.
 */
@Mapper(config = GlobalMapperConfig.class)
public interface CategoryHierarchyMapper {
    /**
     * Converts a CategoryHierarchyRequestDTO to a CategoryHierarchy entity.
     *
     * @param categoryHierarchyRequestDTO the CategoryHierarchyRequestDTO to convert
     * @return the converted CategoryHierarchy entity
     */
    CategoryHierarchy toEntity(CategoryHierarchyRequestDTO categoryHierarchyRequestDTO);

    /**
     * Converts a CategoryHierarchy entity to a CategoryHierarchyResponseBasicDTO.
     *
     * @param categoryHierarchy the CategoryHierarchy entity to convert
     * @return the converted CategoryHierarchyResponseBasicDTO
     */
    @Mapping(target = "categoryID", source = "category.id")
    @Mapping(target = "parentCategoryID", source = "parentCategory.id")
    @Named("basicCategoryHierarchy")
    CategoryHierarchyResponseBasicDTO toBasicDTO(CategoryHierarchy categoryHierarchy);

    /**
     * Converts a CategoryHierarchy entity to a CategoryHierarchyResponseDetailDTO.
     *
     * @param categoryHierarchy the CategoryHierarchy entity to convert
     * @return the converted CategoryHierarchyResponseDetailDTO
     */
    @Mapping(target = "categoryID", source = "category.id")
    @Mapping(target = "parentCategoryID", source = "parentCategory.id")
    @Named("detailCategoryHierarchy")
    CategoryHierarchyResponseDetailDTO toDetailDTO(CategoryHierarchy categoryHierarchy);
}
