package com.tablebookingservice.auth.service;

import com.tablebookingservice.auth.dto.LoginInput;
import com.tablebookingservice.auth.type.MemberType;
import com.tablebookingservice.global.exception.CustomException;
import com.tablebookingservice.manager.entity.ManagerEntity;
import com.tablebookingservice.manager.repository.ManagerRepository;
import com.tablebookingservice.user.entity.UserEntity;
import com.tablebookingservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tablebookingservice.auth.type.MemberType.*;
import static com.tablebookingservice.global.type.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // 매니저 로그인 시 이름/패스워드 확인
    public ManagerEntity authenticateManager(LoginInput input) {
        ManagerEntity manager = checkManagerUsername(input.getUsername());

        if (!this.passwordEncoder.matches(input.getPassword(), manager.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        return manager;
    }

    // 유저 로그인 시 이름/패스워드 확인
    public UserEntity authenticateCustomer(LoginInput input) {
        UserEntity user = checkUserUsername(input.getUsername());

        if (!this.passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        return user;
    }

    // 유저 이름으로 정보 조회
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (this.managerRepository.existsByUsername(username)) {
            ManagerEntity manager = checkManagerUsername(username);

            return createUserDetails(manager.getUsername(),
                    manager.getPassword(), MANAGER);
        } else if (this.userRepository.existsByUsername(username)) {
            UserEntity user = checkUserUsername(username);

            return createUserDetails(user.getUsername(),
                    user.getPassword(), USER);
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    // 유저 정보 객체 생성
    private UserDetails createUserDetails(String username, String password, MemberType memberType) {
        return User.withUsername(username)
                .password(this.passwordEncoder.encode(password))
                .roles(String.valueOf(memberType))
                .build();
    }

    // 매니저 사용자 이름 확인
    private ManagerEntity checkManagerUsername(String username) {
        return this.managerRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(MANAGER_NOT_FOUND));
    }

    // 유저 사용자 이름 확인
    private UserEntity checkUserUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
