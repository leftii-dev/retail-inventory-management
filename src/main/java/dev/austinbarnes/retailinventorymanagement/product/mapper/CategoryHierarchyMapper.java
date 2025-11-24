package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Category;
import dev.austinbarnes.retailinventorymanagement.product.entity.CategoryHierarchy;
import dev.austinbarnes.retailinventorymanagement.product.repo.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * CategoryHierarchyMapper is an interface that defines the mapping between the CategoryHierarchy entity and its corresponding DTOs.
 * It uses MapStruct to generate the implementation at compile time.
 * The mappings include converting a CategoryHierarchyRequestDTO to a CategoryHierarchy entity and converting a CategoryHierarchy entity to
 * both a basic and detailed CategoryHierarchyResponseDTO.
 */
@Mapper(config = GlobalMapperConfig.class)
public abstract class CategoryHierarchyMapper {
    @Autowired
    protected CategoryRepository categoryRepository;

    /**
     * Converts a CategoryHierarchyRequestDTO to a CategoryHierarchy entity.
     *
     * @param categoryHierarchyRequestDTO the CategoryHierarchyRequestDTO to convert
     * @return the converted CategoryHierarchy entity
     */
    @Mapping(target = "category", source = "categoryID")
    @Mapping(target = "parentCategory", source = "parentCategoryID")
    public abstract CategoryHierarchy toEntity(CategoryHierarchyRequestDTO categoryHierarchyRequestDTO);

    /**
     * Converts a CategoryHierarchy entity to a CategoryHierarchyResponseBasicDTO.
     *
     * @param categoryHierarchy the CategoryHierarchy entity to convert
     * @return the converted CategoryHierarchyResponseBasicDTO
     */
    @Mapping(target = "categoryID", source = "category.id")
    @Mapping(target = "parentCategoryID", source = "parentCategory.id")
    @Named("basicCategoryHierarchy")
    public abstract CategoryHierarchyResponseBasicDTO toBasicDTO(CategoryHierarchy categoryHierarchy);

    /**
     * Converts a CategoryHierarchy entity to a CategoryHierarchyResponseDetailDTO.
     *
     * @param categoryHierarchy the CategoryHierarchy entity to convert
     * @return the converted CategoryHierarchyResponseDetailDTO
     */
    @Mapping(target = "categoryID", source = "category.id")
    @Mapping(target = "parentCategoryID", source = "parentCategory.id")
    @Named("detailCategoryHierarchy")
    public abstract CategoryHierarchyResponseDetailDTO toDetailDTO(CategoryHierarchy categoryHierarchy);

    /**
     * Updates an existing CategoryHierarchy entity with the values from the CategoryHierarchyRequestDTO.
     *
     * @param categoryHierarchyRequestDTO the CategoryHierarchyRequestDTO containing the new values
     * @param categoryHierarchy           the CategoryHierarchy entity to update
     */
    @Mapping(target = "category", source = "categoryID")
    @Mapping(target = "parentCategory", source = "parentCategoryID")
    public abstract void updateEntityFromRequest(CategoryHierarchyRequestDTO categoryHierarchyRequestDTO, @MappingTarget CategoryHierarchy categoryHierarchy);

    /**
     * Custom Resolver
     */
    protected Category resolveCategory(UUID categoryID) {
        if(categoryID == null) {
            return null;
        }
        return categoryRepository.findById(categoryID).orElse(null);
    }
}
