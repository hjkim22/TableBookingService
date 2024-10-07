package com.tablebookingservice.user.service;

import com.tablebookingservice.user.dto.RegisterUser;
import com.tablebookingservice.user.dto.UserDto;

public interface UserService {
    UserDto register(RegisterUser registerUser);
    UserDto memberDetail(Long userId);
}
