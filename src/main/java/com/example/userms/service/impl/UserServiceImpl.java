package com.example.userms.service.impl;

import com.example.userms.config.security.JwtCredentials;
import com.example.userms.config.security.JwtService;
import com.example.userms.entity.UserEntity;
import com.example.userms.enums.UserRole;
import com.example.userms.model.dto.UserDto;
import com.example.userms.model.request.UserRequest;
import com.example.userms.repository.UserRepository;
import com.example.userms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final String USER_NOT_FOUND_MSG = "user with this email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void register(UserRequest userRequest) {
        var user = userRepository.findByUsername(userRequest.getUsername());
        if (user.isPresent()) {
            throw new RuntimeException("User already taken");
        }
        userRepository.save(UserEntity.builder()
                .userRole(UserRole.USER)
                .username(userRequest.getUsername())
                .locked(false)
                .password(bCryptPasswordEncoder.encode(userRequest.getPassword()))
                .build());
    }

    @Override
    public UserDto logIn(UserRequest request) {
        var userDetails = (UserEntity) loadUserByUsername(request.getUsername());
        boolean matches = bCryptPasswordEncoder.matches(request.getPassword(), userDetails.getPassword());
        if (!matches) {
            throw new RuntimeException("Email or password is invalid");
        }
        return UserDto.builder()
                .jwt(jwtService.issueToken(JwtCredentials.builder()
                        .role(userDetails.getUserRole())
                        .username(request.getUsername())
                        .build()))
                .build();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }


}

