package dev.austinbarnes.retailinventorymanagement.auth.mapper;

import dev.austinbarnes.retailinventorymanagement.auth.dto.RegistrationRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserResponseBasicDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserResponseDetailDto;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * UserMapper is an interface for mapping between User entities and their DTO representations.
 * It provides methods to convert UserRequestDto and RegistrationRequestDto to User entities,
 * as well as methods to convert User entities to UserResponseBasicDto and UserResponseDetailDto.
 */
@Mapper(config = GlobalMapperConfig.class, uses = {RoleMapper.class})
public interface UserMapper {
    /**
     * Converts a UserRequestDto to a User entity.
     *
     * @param userRequestDto the UserRequestDto to convert
     * @return the converted User entity
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequestDto userRequestDto);

    /**
     * Converts a RegistrationRequestDto to a User entity without roles.
     *
     * @param registrationRequestDto the RegistrationRequestDto to convert
     * @param passwordEncoder         the PasswordEncoder to use for password encoding
     * @return the converted User entity without roles
     */
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registrationRequestDto.password()))")
    User toEntityWithoutRoles(RegistrationRequestDto registrationRequestDto, PasswordEncoder passwordEncoder);

    /**
     * Converts a RegistrationRequestDto to a User entity.
     *
     * @param registrationRequestDto the RegistrationRequestDto to convert
     * @param roleRepository         the RoleRepository to use for role conversion
     * @param passwordEncoder        the PasswordEncoder to use for password encoding
     * @return the converted User entity
     */
    @Mapping(target = "roles", source = ".", qualifiedByName = "toRoleSet")
    default User toEntity(RegistrationRequestDto registrationRequestDto, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        User user = toEntityWithoutRoles(registrationRequestDto, passwordEncoder);
        RoleMapper roleMapper = getRoleMapper();
        user.setRoles(roleMapper.toRoleSet(registrationRequestDto.roles(), roleRepository));
        return user;
    }

    /**
     * Converts a User entity to a UserResponseBasicDto.
     *
     * @param user the User entity to convert
     * @return the converted UserResponseBasicDto
     */
    @Named("basicUser")
    UserResponseBasicDto toBasicDto(User user);

    /**
     * Converts a User entity to a UserResponseDetailDto.
     *
     * @param user the User entity to convert
     * @return the converted UserResponseDetailDto
     */
    @Named("detailUser")
    @Mapping(target = "employeeId", source = "employee.id")
    UserResponseDetailDto toDetailDto(User user);

    @ObjectFactory
    default RoleMapper getRoleMapper() {
        return new RoleMapper() {};
    }

    /**
     * Updates an existing User entity with the values from the UserRequestDto.
     *
     * @param userRequestDto the UserRequestDto containing the new values
     * @param user           the User entity to update
     */
    void updateEntityFromRequest(UserRequestDto userRequestDto, @MappingTarget User user);
}
