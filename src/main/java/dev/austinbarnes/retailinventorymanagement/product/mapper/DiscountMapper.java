package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface DiscountMapper {
    Discount toEntity(DiscountRequestDTO discountRequestDTO);

    @Named("basicDiscount")
    DiscountResponseBasicDTO toBasicDTO(Discount discount);

    @Named("detailDiscount")
    DiscountResponseDetailDTO toDetailDTO(Discount discount);
}
