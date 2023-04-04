package com.example.userms.mapper;

import com.example.userms.entity.PublisherEntity;
import com.example.userms.model.dto.PublisherDto;
import com.example.userms.model.request.PublisherRequest;

public class PublisherMapper {
    public static PublisherDto mapToResponse(PublisherEntity publisher) {
        return PublisherDto.builder()
                .id(publisher.getId())
                .username(publisher.getUsername())
                .password(publisher.getPassword())
                .build();
    }

    public static PublisherEntity mapToEntity(PublisherRequest publisherRequest) {
        return PublisherEntity.builder()
                .username(publisherRequest.getUsername())
                .password(publisherRequest.getPassword())
                .build();
    }
}
