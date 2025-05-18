package dev.austinbarnes.retailinventorymanagement.auth.mapper;

import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * RoleMapper is an interface for mapping between Role entities and their string representations.
 * It provides methods to convert a set of role names to a set of Role entities and vice versa.
 */
@Mapper(config = GlobalMapperConfig.class)
public interface RoleMapper {

    /**
     * Converts a set of role names to a set of Role entities.
     *
     * @param roles          the set of role names to convert
     * @param roleRepository the RoleRepository to use for role conversion
     * @return the converted set of Role entities
     */
    @Named("toRoleSet")
    default Set<Role> toRoleSet(Set<String> roles, RoleRepository roleRepository) {
        return roles.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: %s".formatted(roleName))))
                .collect(Collectors.toSet());
    }

    /**
     * Converts a set of Role entities to a set of role names.
     *
     * @param roles the set of Role entities to convert
     * @return the converted set of role names
     */
    @Named("toRoleNameSet")
    default Set<String> toRoleNameSet(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
