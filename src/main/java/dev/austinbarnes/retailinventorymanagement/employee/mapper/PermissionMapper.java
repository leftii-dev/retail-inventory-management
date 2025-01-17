package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.PermissionRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.PermissionResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.permission.PermissionResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Permission;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface PermissionMapper {
    Permission toEntity(PermissionRequestDTO permissionRequestDTO);

    @Named("basicPermission")
    PermissionResponseBasicDTO toBasicDTO(Permission permission);

    @Named("detailPermission")
    PermissionResponseDetailDTO toDetailDTO(Permission permission);
}
