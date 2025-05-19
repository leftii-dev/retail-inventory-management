package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Inventory;
import dev.austinbarnes.retailinventorymanagement.location.mapper.LocationMapper;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * InventoryMapper is an interface that defines the mapping between Inventory entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert InventoryRequestDTO to Inventory entity and
 * to convert Inventory entity to different types of InventoryResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class, LocationMapper.class})
public interface InventoryMapper {
    /**
     * Converts InventoryRequestDTO to Inventory entity.
     *
     * @param inventoryRequestDTO the InventoryRequestDTO to convert
     * @return the converted Inventory entity
     */
    Inventory toEntity(InventoryRequestDTO inventoryRequestDTO);

    /**
     * Converts Inventory entity to InventoryResponseBasicDTO.
     *
     * @param inventory the Inventory entity to convert
     * @return the converted InventoryResponseBasicDTO
     */
    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicInventory")
    InventoryResponseBasicDTO toBasicDTO(Inventory inventory);

    /**
     * Converts Inventory entity to InventoryResponseDetailDTO.
     *
     * @param inventory the Inventory entity to convert
     * @return the converted InventoryResponseDetailDTO
     */
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailInventory")
    InventoryResponseDetailDTO toDetailDTO(Inventory inventory);
}
