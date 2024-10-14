package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.inventory.InventoryResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Inventory;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.ScopeMetadata;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toEntity(InventoryRequestDTO inventoryRequestDTO);
    InventoryResponseBasicDTO toBasicDTO(Inventory inventory);
    InventoryResponseDetailDTO toDetailDTO(Inventory inventory);
}
