package com.example.userms.service.impl;

import com.example.userms.config.security.JwtCredentials;
import com.example.userms.config.security.JwtService;
import com.example.userms.entity.PublisherEntity;
import com.example.userms.entity.UserEntity;
import com.example.userms.enums.UserRole;
import com.example.userms.mapper.PublisherMapper;
import com.example.userms.mapper.UserMapper;
import com.example.userms.model.dto.PublisherDto;
import com.example.userms.model.dto.UserDto;
import com.example.userms.model.request.PublisherRequest;
import com.example.userms.model.request.UserRequest;
import com.example.userms.repository.PublisherRepository;
import com.example.userms.repository.UserRepository;
import com.example.userms.service.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublisherServiceImpl implements PublisherService, UserDetailsService {
    private static final String PUBLISHER_NOT_FOUND_MSG = "publisher with this email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final PublisherRepository publisherRepository;

    @Override
    public void register(PublisherRequest publisherRequest) {
        var user = publisherRepository.findByUsername(publisherRequest.getUsername());
        if (user.isPresent()) {
            throw new RuntimeException("User already taken");
        }
        publisherRepository.save(PublisherEntity.builder()
                .userRole(UserRole.PUBLISHER)
                .username(publisherRequest.getUsername())
                .locked(false)
                .password(bCryptPasswordEncoder.encode(publisherRequest.getPassword()))
                .build());
    }

    @Override
    public PublisherDto logIn(PublisherRequest request) {
        var userDetails = (UserEntity) loadUserByUsername(request.getUsername());
        boolean matches = bCryptPasswordEncoder.matches(request.getPassword(), userDetails.getPassword());
        if (!matches) {
            throw new RuntimeException("Email or password is invalid");
        }
        return PublisherDto.builder()
                .jwt(jwtService.issueToken(JwtCredentials.builder()
                        .role(userDetails.getUserRole())
                        .username(request.getUsername())
                        .build()))
                .build();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return publisherRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(PUBLISHER_NOT_FOUND_MSG, username)));
    }
}
