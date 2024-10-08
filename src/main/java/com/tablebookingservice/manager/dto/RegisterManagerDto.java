package com.tablebookingservice.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterManagerDto {
    private String username;
    private String password;
    private String phoneNumber;

    public RegisterManagerDto fromManagerDto(ManagerDto managerDto) {
        return RegisterManagerDto.builder()
                .username(managerDto.getUsername())
                .password(managerDto.getPassword())
                .phoneNumber(managerDto.getPhoneNumber())
                .build();
    }
}
