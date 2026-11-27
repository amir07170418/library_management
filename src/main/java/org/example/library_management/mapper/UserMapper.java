package org.example.library_management.mapper;

import org.example.library_management.dto.UserRequest;
import org.example.library_management.dto.UserResponse;
import org.example.library_management.dto.UserUpdate;
import org.example.library_management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
    @Mapping(target = "id", ignore = true)
    User toUser(UserRequest userRequest);
    @Mapping(target = "id", ignore = true)
    User updateUser(UserUpdate userUpdate, @MappingTarget User user);
}
