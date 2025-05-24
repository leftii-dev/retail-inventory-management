package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * DiscountMapper is an interface that defines the mapping between the Discount entity and its corresponding DTOs.
 * It uses MapStruct to generate the implementation at compile time.
 * The mappings include converting a DiscountRequestDTO to a Discount entity and converting a Discount entity to
 * both a basic and detailed DiscountResponseDTO.
 */
@Mapper(config = GlobalMapperConfig.class)
public interface DiscountMapper {
    /**
     * Converts a DiscountRequestDTO to a Discount entity.
     *
     * @param discountRequestDTO the DiscountRequestDTO to convert
     * @return the converted Discount entity
     */
    Discount toEntity(DiscountRequestDTO discountRequestDTO);

    /**
     * Converts a Discount entity to a DiscountResponseBasicDTO.
     *
     * @param discount the Discount entity to convert
     * @return the converted DiscountResponseBasicDTO
     */
    @Named("basicDiscount")
    DiscountResponseBasicDTO toBasicDTO(Discount discount);

    /**
     * Converts a Discount entity to a DiscountResponseDetailDTO.
     *
     * @param discount the Discount entity to convert
     * @return the converted DiscountResponseDetailDTO
     */
    @Named("detailDiscount")
    DiscountResponseDetailDTO toDetailDTO(Discount discount);

    /**
     * Updates an existing Discount entity with the values from the DiscountRequestDTO.
     *
     * @param discountRequestDTO the DiscountRequestDTO containing the new values
     * @param discount           the Discount entity to update
     */
    void updateEntityFromRequest(DiscountRequestDTO discountRequestDTO, @MappingTarget Discount discount);
}
