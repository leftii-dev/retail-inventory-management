package dev.austinbarnes.retailinventorymanagement.product.mapper;

import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.product.ProductResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequestDTO productRequestDTO);
    ProductResponseBasicDTO toBasicDTO(Product product);
    ProductResponseDetailDTO toDetailDTO(Product product);
}
