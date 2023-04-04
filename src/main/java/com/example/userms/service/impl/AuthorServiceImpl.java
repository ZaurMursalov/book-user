package com.example.userms.service.impl;

import com.example.userms.config.security.JwtCredentials;
import com.example.userms.config.security.JwtService;
import com.example.userms.entity.AuthorEntity;
import com.example.userms.enums.UserRole;
import com.example.userms.exception.ExceptionConstants;
import com.example.userms.exception.NotFoundException;
import com.example.userms.model.dto.AuthorDto;
import com.example.userms.model.request.AuthorRequest;
import com.example.userms.repository.AuthorRepository;
import com.example.userms.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService, UserDetailsService {
    private static final String AUTHOR_NOT_FOUND_MSG = "author with this email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final AuthorRepository authorRepository;


    @Override
    public AuthorDto logIn(AuthorRequest request) {
        var userDetails = (AuthorEntity) loadUserByUsername(request.getUsername());
        boolean matches = bCryptPasswordEncoder.matches(request.getPassword(), userDetails.getPassword());
        if (!matches) {
            throw new NotFoundException("Email or password is invalid", ExceptionConstants.UNEXPECTED_EXCEPTION_CODE);
        }
        return AuthorDto.builder()
                .jwt(jwtService.issueToken(JwtCredentials.builder()
                        .role(userDetails.getUserRole())
                        .username(request.getUsername())
                        .build()))
                .build();

    }

    @Override
    public void register(AuthorRequest authorRequest) {
        var user = authorRepository.findByUsername(authorRequest.getUsername());
        if (user.isPresent()) {
            throw new RuntimeException("User already taken");
        }
        authorRepository.save(AuthorEntity.builder()
                .userRole(UserRole.AUTHOR)
                .username(authorRequest.getUsername())
                .locked(false)
                .password(bCryptPasswordEncoder.encode(authorRequest.getPassword()))
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authorRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(AUTHOR_NOT_FOUND_MSG, username)));
    }
}

