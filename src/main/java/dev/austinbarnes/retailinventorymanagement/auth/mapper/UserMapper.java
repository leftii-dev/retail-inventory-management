package dev.austinbarnes.retailinventorymanagement.auth.mapper;

import dev.austinbarnes.retailinventorymanagement.auth.dto.RegistrationRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserResponseBasicDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserResponseDetailDto;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(config = GlobalMapperConfig.class, uses = {RoleMapper.class})
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registrationRequestDto.password()))")
    User toEntityWithoutRoles(RegistrationRequestDto registrationRequestDto, PasswordEncoder passwordEncoder);

    @Mapping(target = "roles", source = ".", qualifiedByName = "toRoleSet")
    default User toEntity(RegistrationRequestDto registrationRequestDto, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        User user = toEntityWithoutRoles(registrationRequestDto, passwordEncoder);
        RoleMapper roleMapper = getRoleMapper();
        user.setRoles(roleMapper.toRoleSet(registrationRequestDto.roles(), roleRepository));
        return user;
    }

    @Named("basicUser")
    UserResponseBasicDto toBasicDto(User user);

    @Named("detailUser")
    @Mapping(target = "employeeId", source = "employee.id")
    UserResponseDetailDto toDetailDto(User user);

    @ObjectFactory
    default RoleMapper getRoleMapper() {
        return new RoleMapper() {};
    }
}
