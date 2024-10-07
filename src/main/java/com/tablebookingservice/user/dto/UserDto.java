package com.tablebookingservice.user.dto;

import com.tablebookingservice.auth.type.MemberType;
import com.tablebookingservice.user.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private MemberType memberType;
    private String password;
    private String phoneNumber;

    public static UserDto fromEntity(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .memberType(user.getMemberType())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
