package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.role.RoleRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.role.RoleResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.role.RoleResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleRequestDTO roleRequestDTO);
    RoleResponseBasicDTO toBasicDTO(Role role);
    RoleResponseDetailDTO toDetailDTO(Role role);
}
