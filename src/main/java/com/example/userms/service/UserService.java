package com.example.userms.service;

import com.example.userms.model.dto.UserDto;
import com.example.userms.model.request.UserRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto logIn (UserRequest request);

    void register(UserRequest userRequest);


//     void register(RegistrationRequestDto request);
//
//     LoginResponseDto login(RegistrationRequestDto request);

}
