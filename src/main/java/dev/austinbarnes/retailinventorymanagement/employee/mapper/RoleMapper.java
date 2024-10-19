package dev.austinbarnes.retailinventorymanagement.employee.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.dto.role.RoleRequestDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.role.RoleResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.role.RoleResponseDTO;
import dev.austinbarnes.retailinventorymanagement.employee.dto.role.RoleResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.employee.entity.Role;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapper;
import dev.austinbarnes.retailinventorymanagement.mapper.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface RoleMapper {
    Role toEntity(RoleRequestDTO roleRequestDTO);

    @Named("basicRole")
    RoleResponseBasicDTO toBasicDTO(Role role);

    @Mapping(target = "createdByID", source = "createdBy.id")
    @Mapping(target = "createdByName", expression = "java(role.getCreatedBy().getNameLast() + \", \" + role.getCreatedBy().getNameFirst())")
    @Mapping(target = "modifiedByID", source = "modifiedBy.id")
    @Mapping(target = "modifiedByName", expression = "java(role.getModifiedBy().getNameLast() + \", \" + role.getModifiedBy().getNameFirst())")
    @Named("detailRole")
    RoleResponseDetailDTO toDetailDTO(Role role);
}
