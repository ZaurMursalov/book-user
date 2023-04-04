package com.example.userms.service;

import com.example.userms.model.dto.AuthorDto;
import com.example.userms.model.dto.UserDto;
import com.example.userms.model.request.AuthorRequest;
import com.example.userms.model.request.UserRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    AuthorDto logIn (AuthorRequest request);

    void register(AuthorRequest authorRequest);


}
