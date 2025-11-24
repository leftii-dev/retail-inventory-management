package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Inventory;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.mapper.LocationMapper;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationRepository;
import dev.austinbarnes.retailinventorymanagement.product.entity.Product;
import dev.austinbarnes.retailinventorymanagement.product.mapper.ProductMapper;
import dev.austinbarnes.retailinventorymanagement.product.repo.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * InventoryMapper is an interface that defines the mapping between Inventory entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert InventoryRequestDTO to Inventory entity and
 * to convert Inventory entity to different types of InventoryResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {ProductMapper.class, LocationMapper.class})
public abstract class InventoryMapper {
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected LocationRepository locationRepository;

    /**
     * Converts InventoryRequestDTO to Inventory entity.
     *
     * @param inventoryRequestDTO the InventoryRequestDTO to convert
     * @return the converted Inventory entity
     */
    @Mapping(target = "product", source = "productID")
    @Mapping(target = "location", source = "locationID")
    public abstract Inventory toEntity(InventoryRequestDTO inventoryRequestDTO);

    /**
     * Converts Inventory entity to InventoryResponseBasicDTO.
     *
     * @param inventory the Inventory entity to convert
     * @return the converted InventoryResponseBasicDTO
     */
    @Mapping(target = "product", qualifiedByName = "basicProduct")
    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicInventory")
    public abstract InventoryResponseBasicDTO toBasicDTO(Inventory inventory);

    /**
     * Converts Inventory entity to InventoryResponseDetailDTO.
     *
     * @param inventory the Inventory entity to convert
     * @return the converted InventoryResponseDetailDTO
     */
    @Mapping(target = "product", qualifiedByName = "detailProduct")
    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Named("detailInventory")
    public abstract InventoryResponseDetailDTO toDetailDTO(Inventory inventory);

    /**
     * Updates an existing Inventory entity from InventoryRequestDTO in-place.
     *
     * @param inventoryRequestDTO the InventoryRequestDTO to convert
     * @param inventory the existing Inventory entity to update
     */
    @Mapping(target = "product", source = "productID")
    @Mapping(target = "location", source = "locationID")
    public abstract void updateEntityFromDTO(InventoryRequestDTO inventoryRequestDTO, @MappingTarget Inventory inventory);

    /**
     * Custom Resolvers
     */
    protected Product resolveProduct(UUID productID) {
        return productID == null ? null : productRepository.findById(productID).orElse(null);
    }

    protected Location resolveLocation(UUID locationID) {
        return locationID == null ? null : locationRepository.findById(locationID).orElse(null);
    }
}
