package com.tablebookingservice.review.controller;

import com.tablebookingservice.review.dto.CreateReview;
import com.tablebookingservice.review.dto.UpdateReview;
import com.tablebookingservice.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// 리뷰 관련 API
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CreateReview.Response createReview(
            @RequestParam(name = "userid") Long userId,
            @RequestParam(name = "storeid") Long storeId,
            @RequestParam(name = "reservationid") Long reservationId,
            @RequestBody CreateReview.Request request) {

        return CreateReview.Response.fromReviewDto(
                this.reviewService.createReview(userId, storeId, reservationId, request));
    }

    // 리뷰 업데이트
    @PutMapping("/update/{reviewId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UpdateReview.Response updateReview(
            @PathVariable Long reviewId, @RequestBody UpdateReview.Request request) {

        return UpdateReview.Response.fromReviewDto(
                this.reviewService.updateReview(reviewId, request));
    }

    // 리뷰 삭제
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        this.reviewService.deleteReview(id);
        return ResponseEntity.ok("리뷰 삭제가 완료되었습니다.");
    }
}