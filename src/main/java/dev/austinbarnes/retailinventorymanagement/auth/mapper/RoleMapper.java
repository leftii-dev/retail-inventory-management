package dev.austinbarnes.retailinventorymanagement.auth.mapper;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = GlobalMapperConfig.class)
public interface RoleMapper {

    @Named("toRoleSet")
    default Set<Role> toRoleSet(Set<String> roles, RoleRepository roleRepository) {
        return roles.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: %s".formatted(roleName))))
                .collect(Collectors.toSet());
    }

    @Named("toRoleNameSet")
    default Set<String> toRoleNameSet(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
