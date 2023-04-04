package com.example.userms.controller;

import com.example.userms.model.dto.AuthorDto;
import com.example.userms.model.request.AuthorRequest;
import com.example.userms.service.impl.AuthorServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/authors")
public class AuthorController {
    private final AuthorServiceImpl authorService;


    @PostMapping("/registration")
    public void register(@RequestBody AuthorRequest request) {
        authorService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthorDto> login(@RequestBody AuthorRequest request) {

        return ResponseEntity.ok(authorService.logIn(request));
    }
}
