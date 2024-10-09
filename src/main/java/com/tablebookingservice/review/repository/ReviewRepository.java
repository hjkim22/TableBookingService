package com.tablebookingservice.review.repository;

import com.tablebookingservice.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    /**
     * @param reservationId 예약 ID
     */
    boolean existsByReservationId(Long reservationId);
}
