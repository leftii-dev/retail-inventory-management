package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Category;
import dev.austinbarnes.retailinventorymanagement.product.entity.Discount;
import dev.austinbarnes.retailinventorymanagement.product.repo.DiscountRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * CategoryMapper is an interface that defines the mapping between the Category entity and its corresponding DTOs.
 * It uses MapStruct to generate the implementation at compile time.
 * The mappings include converting a CategoryRequestDTO to a Category entity and converting a Category entity to
 * both a basic and detailed CategoryResponseDTO.
 */
@Mapper(config = GlobalMapperConfig.class)
public abstract class CategoryMapper {
    @Autowired
    protected DiscountRepository discountRepository;
    /**
     * Converts a CategoryRequestDTO to a Category entity.
     *
     * @param categoryRequestDTO the CategoryRequestDTO to convert
     * @return the converted Category entity
     */
    @Mapping(target = "discount", source = "discountID")
    public abstract Category toEntity(CategoryRequestDTO categoryRequestDTO);

    /**
     * Converts a Category entity to a CategoryResponseBasicDTO.
     *
     * @param category the Category entity to convert
     * @return the converted CategoryResponseBasicDTO
     */
    @Mapping(target = "discountID", source = "discount.id")
    @Named("basicCategory")
    public abstract CategoryResponseBasicDTO toBasicDTO(Category category);

    /**
     * Converts a Category entity to a CategoryResponseDetailDTO.
     *
     * @param category the Category entity to convert
     * @return the converted CategoryResponseDetailDTO
     */
    @Mapping(target = "discountID", source = "discount.id")
    @Named("detailCategory")
    public abstract CategoryResponseDetailDTO toDetailDTO(Category category);

    /**
     * Updates an existing Category entity with the values from the CategoryRequestDTO.
     *
     * @param categoryRequestDTO the CategoryRequestDTO containing the new values
     * @param category           the Category entity to update
     */
    @Mapping(target = "discount", source = "discountID")
    public abstract void updateEntityFromRequest(CategoryRequestDTO categoryRequestDTO, @MappingTarget Category category);

    /**
     * Custom Resolver
     */
    protected Discount resolveDiscount(UUID discountID) {
        if (discountID == null) {
            return null;
        }
        return discountRepository.findById(discountID).orElse(null);
    }
}

