package com.tablebookingservice.review.entity;

import com.tablebookingservice.global.entity.GlobalEntity;
import com.tablebookingservice.reservation.entity.ReservationEntity;
import com.tablebookingservice.store.entity.StoreEntity;
import com.tablebookingservice.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity extends GlobalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500) // 선택 입력
    private String content;

    /**
     * 평점 -> int 1 ~ 5
     */
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;
}
