package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.CategoryHierarchy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryHierarchyMapper {
    CategoryHierarchy toEntity(CategoryHierarchyRequestDTO categoryHierarchyRequestDTO);
    CategoryHierarchyResponseBasicDTO toBasicDTO(CategoryHierarchy categoryHierarchy);
    CategoryHierarchyResponseDetailDTO toDetailDTO(CategoryHierarchy categoryHierarchy);
}
