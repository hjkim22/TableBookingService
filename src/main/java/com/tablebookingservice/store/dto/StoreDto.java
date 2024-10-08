package com.tablebookingservice.store.dto;

import com.tablebookingservice.manager.entity.ManagerEntity;
import com.tablebookingservice.store.entity.StoreEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private ManagerEntity manager;
    private String storeName;
    private String location;
    private String phoneNumber;

    public static StoreDto fromEntity(StoreEntity store) {
        return StoreDto.builder()
                .manager(store.getManager())
                .storeName(store.getStoreName())
                .location(store.getLocation())
                .phoneNumber(store.getPhoneNumber())
                .build();
    }
}
