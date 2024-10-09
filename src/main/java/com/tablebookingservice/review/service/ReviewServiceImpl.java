package com.tablebookingservice.review.service;

import com.tablebookingservice.global.exception.CustomException;
import com.tablebookingservice.reservation.entity.ReservationEntity;
import com.tablebookingservice.reservation.repository.ReservationRepository;
import com.tablebookingservice.review.dto.CreateReview;
import com.tablebookingservice.review.dto.ReviewDto;
import com.tablebookingservice.review.dto.UpdateReview;
import com.tablebookingservice.review.entity.ReviewEntity;
import com.tablebookingservice.review.repository.ReviewRepository;
import com.tablebookingservice.store.entity.StoreEntity;
import com.tablebookingservice.store.repository.StoreRepository;
import com.tablebookingservice.user.entity.UserEntity;
import com.tablebookingservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tablebookingservice.global.type.ErrorCode.*;
import static com.tablebookingservice.reservation.type.ReservationStatus.USE_COMPLETED;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public ReviewDto createReview(Long userId, Long storeId, Long reservationId,
                                  CreateReview.Request request) {
        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        StoreEntity store = this.storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        ReservationEntity reservation = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        validationReviewStatus(user, reservation);

        ReviewEntity savedReview = this.reviewRepository.save(ReviewEntity.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .user(user)
                .store(store)
                .reservation(reservation)
                .build());

        return ReviewDto.fromEntity(savedReview);
    }

    @Override
    @Transactional
    public ReviewDto updateReview(Long reviewId, UpdateReview.Request request) {
        ReviewEntity review = this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        validationUpdateReview(request);

        review.setRating(request.getRating());
        review.setContent(request.getContent());
        ReviewEntity savedReview = this.reviewRepository.save(review);

        return ReviewDto.fromEntity(savedReview);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        this.reviewRepository.delete(this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND)));
    }

    private void validationUpdateReview(UpdateReview.Request request) {
        // 평점 범위 검사 (1 ~ 5)
        if (request.getRating() > 5 || request.getRating() < 1) {
            throw new CustomException(REVIEW_RATING_RANGE_OVER);
        }
        if (request.getContent().length() > 500) {
            throw new CustomException(REVIEW_TEXT_TOO_LONG);
        }
    }

    private void validationReviewStatus(UserEntity customer, ReservationEntity reservation) {
        if (!reservation.getUser().getId().equals(customer.getId())) {
            throw new CustomException(USER_AUTHORITY_NOT_MATCH);
        }
        if (this.reviewRepository.existsByReservationId(reservation.getId())) {
            throw new CustomException(ALREADY_EXIST_REVIEW);
        }
        if (!reservation.getReservationStatus().equals(USE_COMPLETED)) {
            throw new CustomException(REVIEW_NOT_AVAILABLE);
        }
    }
}