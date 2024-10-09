package com.tablebookingservice.manager.service;

import com.tablebookingservice.global.exception.CustomException;
import com.tablebookingservice.manager.dto.ManagerDto;
import com.tablebookingservice.manager.dto.RegisterManagerDto;
import com.tablebookingservice.manager.entity.ManagerEntity;
import com.tablebookingservice.manager.repository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.tablebookingservice.global.type.ErrorCode.MANAGER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerServiceImplTest {

    @InjectMocks
    private ManagerServiceImpl managerService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ManagerRepository managerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterManagerDto registerDto = new RegisterManagerDto("manager1", "password", "123456789");
        registerDto.setUsername("manager1");
        registerDto.setPassword("password");
        registerDto.setPhoneNumber("123456789");

        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(managerRepository.save(any())).thenReturn(new ManagerEntity());

        ManagerDto managerDto = managerService.register(registerDto);

        assertNotNull(managerDto);
        verify(managerRepository).save(any());
    }

    @Test
    void testMemberDetail() {
        Long managerId = 1L;
        ManagerEntity manager = new ManagerEntity();
        manager.setId(managerId);

        when(managerRepository.findById(managerId)).thenReturn(java.util.Optional.of(manager));

        ManagerDto managerDto = managerService.memberDetail(managerId);

        assertNotNull(managerDto);
        assertEquals(managerId, managerDto.getId());
    }

    @Test
    void testMemberDetailThrowsException() {
        Long managerId = 1L;

        when(managerRepository.findById(managerId)).thenReturn(java.util.Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> managerService.memberDetail(managerId));
        assertEquals(MANAGER_NOT_FOUND, exception.getErrorCode());
    }
}
