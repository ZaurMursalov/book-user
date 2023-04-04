package com.example.userms.controller;

import com.example.userms.model.dto.PublisherDto;
import com.example.userms.model.dto.UserDto;
import com.example.userms.model.request.PublisherRequest;
import com.example.userms.model.request.UserRequest;
import com.example.userms.service.PublisherService;
import com.example.userms.service.impl.PublisherServiceImpl;
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
@RequestMapping("v1/publishers")
public class PublisherController {
    private final PublisherServiceImpl publisherService;

    @PostMapping("/registration")
    public void register(@RequestBody PublisherRequest request) {
        publisherService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<PublisherDto> login(@RequestBody PublisherRequest request){

        return ResponseEntity.ok(publisherService.logIn(request));
    }
}
