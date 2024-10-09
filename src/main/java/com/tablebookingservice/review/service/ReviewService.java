package com.tablebookingservice.review.service;

import com.tablebookingservice.review.dto.CreateReview;
import com.tablebookingservice.review.dto.ReviewDto;
import com.tablebookingservice.review.dto.UpdateReview;

public interface ReviewService {

    ReviewDto createReview(Long userId, Long storeId, Long reservationId,
                           CreateReview.Request request);

    ReviewDto updateReview(Long reviewId, UpdateReview.Request request);

    void deleteReview(Long reviewId);
}