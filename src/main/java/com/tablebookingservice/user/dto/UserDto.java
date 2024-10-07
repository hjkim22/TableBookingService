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

    public static UserDto fromEntity(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .memberType(userEntity.getMemberType())
                .password(userEntity.getPassword())
                .phoneNumber(userEntity.getPhoneNumber())
                .build();
    }
}
