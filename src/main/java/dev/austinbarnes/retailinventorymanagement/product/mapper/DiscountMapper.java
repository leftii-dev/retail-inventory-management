package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.discount.DiscountResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Discount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    Discount toEntity(DiscountRequestDTO discountRequestDTO);
    DiscountResponseBasicDTO toBasic(Discount discount);
    DiscountResponseDetailDTO toDetail(Discount discount);
}
