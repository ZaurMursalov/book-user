package com.example.userms.controller;

import com.example.userms.entity.UserEntity;
import com.example.userms.model.dto.UserDto;
import com.example.userms.model.request.UserRequest;
import com.example.userms.service.impl.UserServiceImpl;
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
@RequestMapping("v1/users")
public class UserController {
    private final UserServiceImpl userService;


    @PostMapping("/registration")
    public void register(@RequestBody UserRequest request) {
        userService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity <UserDto> login(@RequestBody UserRequest request){

        return ResponseEntity.ok(userService.logIn(request));
    }

}
