package com.tablebookingservice.manager.dto;

import com.tablebookingservice.global.type.MemberType;
import com.tablebookingservice.manager.entity.ManagerEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {
    private Long id;
    private String username;
    private MemberType memberType;
    private String password;
    private String phoneNumber;

    public static ManagerDto fromEntity(ManagerEntity managerEntity) {
        return ManagerDto.builder()
                .id(managerEntity.getId())
                .username(managerEntity.getUsername())
                .memberType(managerEntity.getMemberType())
                .password(managerEntity.getPassword())
                .phoneNumber(managerEntity.getPhoneNumber())
                .build();
    }
}
