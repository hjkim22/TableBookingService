package com.tablebookingservice.manager.service;

import com.tablebookingservice.manager.dto.ManagerDto;
import com.tablebookingservice.manager.dto.RegisterManagerDto;

public interface ManagerService {

    ManagerDto register(RegisterManagerDto registerManagerDto);
    ManagerDto memberDetail(Long userId)
}
