package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.PermissionRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.PermissionResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.PermissionResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * PermissionMapper is an interface for mapping between Permission entities and their DTO representations.
 * It provides methods to convert PermissionRequestDTO to Permission entities,
 * as well as methods to convert Permission entities to PermissionResponseBasicDTO and PermissionResponseDetailDTO.
 */
@Mapper(config = GlobalMapperConfig.class)
public interface PermissionMapper {
    /**
     * Converts a PermissionRequestDTO to a Permission entity.
     *
     * @param permissionRequestDTO the PermissionRequestDTO to convert
     * @return the converted Permission entity
     */
    Permission toEntity(PermissionRequestDTO permissionRequestDTO);

    /**
     * Converts a Permission entity to a PermissionResponseBasicDTO.
     */
    @Named("basicPermission")
    PermissionResponseBasicDTO toBasicDTO(Permission permission);

    /**
     * Converts a Permission entity to a PermissionResponseDetailDTO.
     */
    @Named("detailPermission")
    PermissionResponseDetailDTO toDetailDTO(Permission permission);

    /**
     * Updates an existing Permission entity with the values from the PermissionRequestDTO.
     *
     * @param permissionRequestDTO the PermissionRequestDTO containing the new values
     * @param permission           the Permission entity to update
     */
    void updateEntityFromRequest(PermissionRequestDTO permissionRequestDTO, @MappingTarget Permission permission);
}
