package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.*;
import dev.austinbarnes.retailinventorymanagement.product.entity.*;
import dev.austinbarnes.retailinventorymanagement.product.repo.BrandRepository;
import dev.austinbarnes.retailinventorymanagement.product.repo.CategoryRepository;
import dev.austinbarnes.retailinventorymanagement.product.repo.DiscountRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ProductMapper is an interface that defines the mapping between the Product entity and its corresponding DTOs.
 * It uses MapStruct to generate the implementation at compile time.
 * The mappings include converting a ProductRequestDTO to a Product entity and converting a Product entity to
 * both a basic and detailed ProductResponseDTO.
 */
@Mapper(config = GlobalMapperConfig.class)
public abstract class ProductMapper {

    @Autowired
    protected BrandRepository brandRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected DiscountRepository discountRepository;

    /**
     * Converts a ProductRequestDTO to a Product entity.
     *
     * @param productRequestDTO the ProductRequestDTO to convert
     * @return the converted Product entity
     */
    @Mapping(target = "brand", source = "brandID")
    @Mapping(target = "category", source = "categoryID")
    @Mapping(target = "discount", source = "discountID")
    public abstract Product toEntity(ProductRequestDTO productRequestDTO);

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
    @Mapping(target = "images", qualifiedByName = "toImageBasicDTOList")
    @Named("basicProduct")
    public abstract ProductResponseBasicDTO toBasicDTO(Product product);

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
    @Mapping(target = "images", qualifiedByName = "toImageDetailDTOList")
    @Named("detailProduct")
    public abstract ProductResponseDetailDTO toDetailDTO(Product product);

    /**
     * Updates an existing Product entity with the values from the ProductRequestDTO.
     *
     * @param productRequestDTO the ProductRequestDTO containing the new values
     * @param product           the Product entity to update
     */
    @Mapping(target = "brand", source = "brandID")
    @Mapping(target = "category", source = "categoryID")
    @Mapping(target = "discount", source = "discountID")
    public abstract void updateEntityFromRequest(ProductRequestDTO productRequestDTO, @MappingTarget Product product);

    /**
     * Custom Resolvers
     */
    protected Brand resolveBrand(UUID brandID){
        if(brandID == null) return null;
        return brandRepository.findById(brandID).orElse(null);
    }

    protected Category resolveCategory(UUID categoryID){
        if(categoryID == null) return null;
        return categoryRepository.findById(categoryID).orElse(null);
    }

    protected Discount resolveDiscount(UUID discountID){
        if(discountID == null) return null;
        return discountRepository.findById(discountID).orElse(null);
    }

    /**
     * Converts a ProductImageRequestDTO to a ProductImage entity.
     * Note: productId mapping is handled in the service layer when adding a product.
     *
     * @param requestDTO the ProductImageRequestDTO to convert
     * @return the converted ProductImage entity
     */
    public abstract ProductImage toImageEntity(ProductImageRequestDTO requestDTO);

    /**
     * Converts ProductImage entity into ProductImageResponseBasicDTO
     * @param image the ProductImage to convert
     * @return the converted ProductImageResponseBasicDTO
     */
    @Mapping(target = "productId", source = "product.id")
    public abstract ProductImageResponseBasicDTO toImageBasicDTO(ProductImage image);

    /**
     * Converts ProductImage entity into ProductImageResponseDetailDTO
     * @param image the ProductImage to be converted
     * @return the converted ProductImageResponseDetailDTO
     */
    @Mapping(target = "productId", source = "product.id")
    public abstract ProductImageResponseDetailDTO toImageDetailDTO(ProductImage image);

    /**
     * Converts a list of ProductImage entities to a list of ProductImageResponseBasicDTOs.
     *
     * @param images the list of ProductImage entities to convert
     * @return the converted list of ProductImageResponseBasicDTOs
     */
    @Named("toImageBasicDTOList")
    public List<ProductImageResponseDTO> toImageBasicDTOList(List<ProductImage> images) {
        if (images == null) {
            return null;
        }
        return images.stream()
                .map(this::toImageBasicDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of ProductImage entities to a list of ProductImageResponseDTOs (Detail version).
     *
     * @param images the list of ProductImage entities to convert
     * @return the converted list of ProductImageResponseDTOs
     */
    @Named("toImageDetailDTOList")
    public List<ProductImageResponseDTO> toImageDetailDTOList(List<ProductImage> images) {
        if (images == null) {
            return null;
        }
        return images.stream()
                .map(this::toImageDetailDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing ProductImage entity with values from ProductImageRequestDTO.
     * Used for updating image metadata (not the product relationship).
     *
     * @param requestDTO the ProductImageRequestDTO containing the new values
     * @param image      the ProductImage entity to update
     */
    public abstract void updateImageFromRequest(ProductImageRequestDTO requestDTO, @MappingTarget ProductImage image);
}

