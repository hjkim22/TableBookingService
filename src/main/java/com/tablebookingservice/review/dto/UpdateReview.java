package com.tablebookingservice.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UpdateReview {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long reviewId;
        private String content;
        private int rating;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long reviewId;
        private String content;
        private int rating;

        public static Response fromReviewDto(ReviewDto reviewDto) {
            return Response.builder()
                    .reviewId(reviewDto.getReviewId())
                    .content(reviewDto.getContent() != null ? reviewDto.getContent() : "") // null 방어 코드 추가
                    .rating(reviewDto.getRating())
                    .build();
        }
    }
}
