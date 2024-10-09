package com.tablebookingservice.user.service;

import com.tablebookingservice.global.type.MemberType;
import com.tablebookingservice.global.exception.CustomException;
import com.tablebookingservice.user.dto.RegisterUserDto;
import com.tablebookingservice.user.dto.UserDto;
import com.tablebookingservice.user.entity.UserEntity;
import com.tablebookingservice.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tablebookingservice.global.type.ErrorCode.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto register(RegisterUserDto user) {
        // 비밀번호 인코딩 후 저장
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        UserEntity savedUser = this.userRepository.save(UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .memberType(MemberType.USER)
                .build());

        return UserDto.fromEntity(savedUser);
    }

    @Override
    public UserDto memberDetail(Long userId) {
        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return UserDto.fromEntity(user);
    }
}
