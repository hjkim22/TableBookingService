package com.tablebookingservice.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CreateStore {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long managerId;

        @NotBlank
        private String storeName;

        @NotBlank
        private String location;

        @NotBlank
        private String phoneNumber;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String storeName;

        public static Response fromStoreDto(StoreDto storeDto) {
            return Response.builder()
                    .storeName(storeDto.getStoreName())
                    .build();
        }
    }
}
