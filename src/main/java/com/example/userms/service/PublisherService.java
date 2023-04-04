package com.example.userms.service;

import com.example.userms.model.dto.PublisherDto;
import com.example.userms.model.dto.UserDto;
import com.example.userms.model.request.PublisherRequest;
import com.example.userms.model.request.UserRequest;
import org.springframework.stereotype.Service;

@Service
public interface PublisherService {
    PublisherDto logIn (PublisherRequest request);

    void register(PublisherRequest publisherRequest);




}
