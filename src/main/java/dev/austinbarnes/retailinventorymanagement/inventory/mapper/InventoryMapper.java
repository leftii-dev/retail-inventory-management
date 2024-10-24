package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Inventory;
import dev.austinbarnes.retailinventorymanagement.location.mapper.LocationMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class, LocationMapper.class})
public interface InventoryMapper {
    Inventory toEntity(InventoryRequestDTO inventoryRequestDTO);

    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicInventory")
    InventoryResponseBasicDTO toBasicDTO(Inventory inventory);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(inventory.getCreatedBy().getNameLast() + \", \" + inventory.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(inventory.getModifiedBy().getNameLast() + \", \" + inventory.getModifiedBy().getNameFirst())")
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailInventory")
    InventoryResponseDetailDTO toDetailDTO(Inventory inventory);
}
