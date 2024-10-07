package com.tablebookingservice.user.service;

import com.tablebookingservice.user.dto.RegisterUserDto;
import com.tablebookingservice.user.dto.UserDto;

public interface UserService {
    UserDto register(RegisterUserDto registerUserDto);
    UserDto memberDetail(Long userId);
}
