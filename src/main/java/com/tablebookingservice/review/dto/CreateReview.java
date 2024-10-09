package com.tablebookingservice.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CreateReview {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String content;
        private int rating;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long reviewId;
        private String content;
        private int rating;
        private String username;
        private String storeName;

        public static Response fromReviewDto(ReviewDto reviewDto) {
            return Response.builder()
                    .reviewId(reviewDto.getReviewId())
                    .content(reviewDto.getContent() != null ? reviewDto.getContent() : "") // null 방어 코드 추가
                    .rating(reviewDto.getRating())
                    .username(reviewDto.getUsername())
                    .storeName(reviewDto.getStoreName())
                    .build();
        }
    }
}