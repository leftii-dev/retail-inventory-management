package dev.austinbarnes.retailinventorymanagement.auth.mapper;

import dev.austinbarnes.retailinventorymanagement.auth.dto.RegistrationRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserResponseBasicDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserResponseDetailDto;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.common.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequestDto userRequestDto);
    
    User toEntity(RegistrationRequestDto registrationRequestDto);

    @Named("basicUser")
    UserResponseBasicDto toBasicDto(User user);

    @Named("detailUser")
    @Mapping(target = "employeeId", source = "employee.id")
    UserResponseDetailDto toDetailDto(User user);
}
