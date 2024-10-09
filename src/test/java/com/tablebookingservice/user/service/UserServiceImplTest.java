package com.tablebookingservice.user.service;

import com.tablebookingservice.global.exception.CustomException;
import com.tablebookingservice.auth.MemberType;
import com.tablebookingservice.user.dto.RegisterUserDto;
import com.tablebookingservice.user.dto.UserDto;
import com.tablebookingservice.user.entity.UserEntity;
import com.tablebookingservice.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .username("testuser")
                .password("encodedPassword")
                .phoneNumber("1234567890")
                .memberType(MemberType.USER)
                .build();
    }

    @Test
    void testRegister() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto("testuser", "password123", "1234567890");
        registerUserDto.setUsername("testuser");
        registerUserDto.setPassword("password123");
        registerUserDto.setPhoneNumber("1234567890");

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        UserDto result = userService.register(registerUserDto);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testMemberDetail_UserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> userService.memberDetail(userId));
        assertEquals("사용자가 없습니다.", exception.getMessage());
    }
}
