package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.WarehouseLocation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseLocationMapper {
    WarehouseLocation toEntity(WarehouseLocationRequestDTO warehouseLocationRequestDTO);
    WarehouseLocationResponseBasicDTO toBasicDTO(WarehouseLocation warehouseLocation);
    WarehouseLocationResponseDetailDTO toDetailDTO(WarehouseLocation warehouseLocation);
}
