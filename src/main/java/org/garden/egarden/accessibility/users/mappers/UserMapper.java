package org.garden.egarden.accessibility.users.mappers;

import org.garden.egarden.accessibility.roles.entities.RoleName;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.accessibility.users.payloads.RegistrationResponseDto;
import org.garden.egarden.accessibility.users.payloads.UserProfileResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "created", target = "registrationDate"),
            @Mapping(target = "role", expression = "java(getRole(userEntity))"),
    })
    RegistrationResponseDto toRegistrationDto(UserEntity userEntity);

    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(source = "created", target = "registrationDate"),
            @Mapping(target = "role", expression = "java(getRole(userEntity))"),
    })
    UserProfileResponseDto toUserProfileDto(UserEntity userEntity);

    default RoleName getRole(UserEntity user) {
        return user.getRole().getName();
    }
}
