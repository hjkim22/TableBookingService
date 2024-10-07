package com.tablebookingservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data // 데이터를 출력하거나 비교할 필요가 있어서 Data 사용
@Builder
@AllArgsConstructor
// 회원가입에 대한 데이터 전송, 검증을 명확하게 하기 위해 생성
public class RegisterUser {
    private String username;
    private String password;
    private String phoneNumber;

    public RegisterUser fromUserDto(UserDto userDto) {
        return RegisterUser.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }
}
