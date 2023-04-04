package com.example.userms.mapper;

import com.example.userms.entity.UserEntity;
import com.example.userms.model.dto.UserDto;
import com.example.userms.model.request.UserRequest;

public class UserMapper {

    public static UserDto mapToResponse(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public static UserEntity mapToEntity(UserRequest userRequest) {
        return UserEntity.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();
    }
}
