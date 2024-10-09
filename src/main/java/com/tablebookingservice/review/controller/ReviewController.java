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

    /**
     * 리뷰 생성
     * @param userId        리뷰 작성 사용자 ID
     * @param storeId       리뷰 대상 가게 ID
     * @param reservationId 리뷰 관련 예약 ID
     * @param request       리뷰 세부 정보 포함 요청 본문
     * @return              생성 리뷰 포함 응답
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CreateReview.Response createReview(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "storeId") Long storeId,
            @RequestParam(name = "reservationId") Long reservationId,
            @RequestBody CreateReview.Request request) {

        return CreateReview.Response.fromReviewDto(
                this.reviewService.createReview(userId, storeId, reservationId, request));
    }

    /**
     * 리뷰 수정
     * @param reviewId 수정할 리뷰 ID
     * @param request  수정할 리뷰 세부 정보 포함 요청 본문
     * @return         수정된 리뷰 포함 응답
     */
    @PutMapping("/update/{reviewId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UpdateReview.Response updateReview(
            @PathVariable Long reviewId, @RequestBody UpdateReview.Request request) {

        return UpdateReview.Response.fromReviewDto(
                this.reviewService.updateReview(reviewId, request));
    }

    /**
     * 리뷰 삭제
     * @param id 삭제 리뷰 ID
     * @return   삭제 결과 응답
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        this.reviewService.deleteReview(id);
        return ResponseEntity.ok("리뷰 삭제가 완료되었습니다.");
    }
}