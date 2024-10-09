package com.tablebookingservice.review.dto;

import com.tablebookingservice.review.entity.ReviewEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long reviewId;
    private String content;
    private int rating;
    private String username;
    private String storeName;

    public static ReviewDto fromEntity(ReviewEntity review) {
        return ReviewDto.builder()
                .reviewId(review.getId())
                .content(review.getContent() != null ? review.getContent() : "") // null 방어 코드 추가
                .rating(review.getRating())
                .username(review.getUser().getUsername())
                .storeName(review.getStore().getStoreName())
                .build();
    }
}