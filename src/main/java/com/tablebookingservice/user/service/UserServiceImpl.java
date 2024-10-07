package com.tablebookingservice.user.service;

import com.tablebookingservice.auth.type.MemberType;
import com.tablebookingservice.global.exception.UserException;
import com.tablebookingservice.global.type.ErrorCode;
import com.tablebookingservice.user.dto.RegisterUser;
import com.tablebookingservice.user.dto.UserDto;
import com.tablebookingservice.user.entity.UserEntity;
import com.tablebookingservice.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto register(RegisterUser registerUser) {
        // 비밀번호 인코딩 후 저장
        registerUser.setPassword(this.passwordEncoder.encode(registerUser.getPassword()));

        UserEntity savedUser = this.userRepository.save(UserEntity.builder()
                .username(registerUser.getUsername())
                .password(registerUser.getPassword())
                .phoneNumber(registerUser.getPhoneNumber())
                .memberType(MemberType.USER)
                .build());

        return UserDto.fromEntity(savedUser);
    }

    @Override
    public UserDto memberDetail(Long userId) {
        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        return UserDto.fromEntity(user);
    }
}
