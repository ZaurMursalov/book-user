package com.example.userms.mapper;

import com.example.userms.entity.AuthorEntity;
import com.example.userms.entity.UserEntity;
import com.example.userms.model.dto.AuthorDto;
import com.example.userms.model.dto.UserDto;
import com.example.userms.model.request.AuthorRequest;
import com.example.userms.model.request.UserRequest;

public class AuthorMapper {
    public static AuthorDto mapToResponse(AuthorEntity authorEntity) {
        return AuthorDto.builder()
                .id(authorEntity.getId())
                .username(authorEntity.getUsername())
                .password(authorEntity.getPassword())
                .build();
    }

    public static AuthorEntity mapToEntity(AuthorRequest authorRequest) {
        return AuthorEntity.builder()
                .username(authorRequest.getUsername())
                .password(authorRequest.getPassword())
                .build();
    }

}
