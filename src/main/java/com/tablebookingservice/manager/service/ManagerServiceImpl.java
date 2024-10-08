package com.tablebookingservice.manager.service;

import com.tablebookingservice.auth.type.MemberType;
import com.tablebookingservice.global.exception.CustomException;
import com.tablebookingservice.global.type.ErrorCode;
import com.tablebookingservice.manager.dto.ManagerDto;
import com.tablebookingservice.manager.dto.RegisterManagerDto;
import com.tablebookingservice.manager.entity.ManagerEntity;
import com.tablebookingservice.manager.repository.ManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final PasswordEncoder passwordEncoder;
    private final ManagerRepository managerRepository;

    @Override
    @Transactional
    public ManagerDto register(RegisterManagerDto manager) {
        // 비밀번호 인코딩 후 저장
        manager.setPassword(this.passwordEncoder.encode(manager.getPassword()));

        ManagerEntity savedManager = this.managerRepository.save(ManagerEntity.builder()
                .username(manager.getUsername())
                .password(manager.getPassword())
                .phoneNumber(manager.getPhoneNumber())
                .memberType(MemberType.MANAGER)
                .build());

        return ManagerDto.fromEntity(savedManager);
    }

    @Override
    public ManagerDto memberDetail(Long userId) {
        ManagerEntity manager = this.managerRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MANAGER_NOT_FOUND));

        return ManagerDto.fromEntity(manager);
    }
}
