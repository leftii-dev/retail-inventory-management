package dev.austinbarnes.retailinventorymanagement.location.mapper;

import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationRequestDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseDTO;
import dev.austinbarnes.retailinventorymanagement.location.dto.warehouse.WarehouseLocationResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.location.entity.WarehouseLocation;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class, uses = {LocationMapper.class})
public interface WarehouseLocationMapper {
    WarehouseLocation toEntity(WarehouseLocationRequestDTO warehouseLocationRequestDTO);

    @Mapping(target = "location", qualifiedByName = "basicLocation")
    @Named("basicWarehouseLocation")
    WarehouseLocationResponseBasicDTO toBasicDTO(WarehouseLocation warehouseLocation);

    @Mapping(target = "location", qualifiedByName = "detailLocation")
    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(warehouseLocation.getCreatedBy().getNameLast() + \", \" + warehouseLocation.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(warehouseLocation.getModifiedBy().getNameLast() + \", \" + warehouseLocation.getModifiedBy().getNameFirst())")
    @Named("detailWarehouseLocation")
    WarehouseLocationResponseDetailDTO toDetailDTO(WarehouseLocation warehouseLocation);
}
