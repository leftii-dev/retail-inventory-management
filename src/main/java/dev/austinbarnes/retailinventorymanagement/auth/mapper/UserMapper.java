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
     * @param roleRepository the RoleRepository to use for role conversion
     * @return the converted User entity
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", source = "roles", qualifiedByName = "toRoleSet")
    User toEntity(
            UserRequestDto userRequestDto,
            @Context RoleRepository roleRepository);

    /**
     * Converts a RegistrationRequestDto to a User entity.
     *
     * @param registrationRequestDto the RegistrationRequestDto to convert
     * @param roleRepository the RoleRepository to use for role conversion
     * @param passwordEncoder the PasswordEncoder to encode the password
     * @return the converted User entity
     */
    @Mapping(target = "roles", source = "roles", qualifiedByName = "toRoleSet")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registrationRequestDto.password()))")
    User toEntity(
            RegistrationRequestDto registrationRequestDto,
            @Context RoleRepository roleRepository,
            @Context PasswordEncoder passwordEncoder
    );

    /**
     * Converts a User entity to a UserResponseBasicDto.
     *
     * @param user the User entity to convert
     * @param roleRepository the RoleRepository to use for role conversion
     * @return the converted UserResponseBasicDto
     */
    @Named("basicUser")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "toRoleNameSet")
    UserResponseBasicDto toBasicDto(
            User user,
            @Context RoleRepository roleRepository);

    /**
     * Converts a User entity to a UserResponseDetailDto.
     *
     * @param user the User entity to convert
     * @param roleRepository the RoleRepository to use for role conversion
     * @return the converted UserResponseDetailDto
     */
    @Named("detailUser")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "toRoleNameSet")
    UserResponseDetailDto toDetailDto(
            User user,
            @Context RoleRepository roleRepository
    );

    /**
     * Updates a User entity from a UserRequestDto.
     *
     * @param userRequestDto the UserRequestDto containing updated data
     * @param user the User entity to update
     * @param roleRepository the RoleRepository to use for role conversion
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", source = "roles", qualifiedByName = "toRoleSet")
    @Mapping(target = "password", ignore = true)
    void updateEntityFromRequest(
            UserRequestDto userRequestDto,
            @MappingTarget User user,
            @Context RoleRepository roleRepository
    );

    /**
     * Updates a User entity from a UserRequestDto without modifying roles.
     *
     * @param userRequestDto the UserRequestDto containing updated data
     * @param user the User entity to update
     */
    @Mapping(target = "roles", ignore = true)
    void updateEntityFromRequestWithoutRoles(UserRequestDto userRequestDto, @MappingTarget User user);

    /**
     * Updates a User entity from a RegistrationRequestDto without modifying roles or password.
     *
     * @param registrationRequestDto the RegistrationRequestDto containing updated data
     * @param user the User entity to update
     */
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromRegistrationRequestDto(RegistrationRequestDto registrationRequestDto, @MappingTarget User user);
}
