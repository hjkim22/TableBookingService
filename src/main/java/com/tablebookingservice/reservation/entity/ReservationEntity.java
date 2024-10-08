package com.tablebookingservice.reservation.entity;

import com.tablebookingservice.global.entity.GlobalEntity;
import com.tablebookingservice.reservation.type.ArrivalStatus;
import com.tablebookingservice.reservation.type.ReservationStatus;
import com.tablebookingservice.store.entity.StoreEntity;
import com.tablebookingservice.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity extends GlobalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus; // 승인 여부

    @Enumerated(EnumType.STRING)
    private ArrivalStatus arrivalStatus; // 도착 여부

    @NotNull(message = "예약 날짜는 필수입니다.") // 유효성 검사
    @Future(message = "예약 날짜는 현재 날짜 이후여야 합니다.") // 예약 날짜가 미래여야 함
    private LocalDate reservationDate;

    @NotNull(message = "예약 시간은 필수입니다.") // 유효성 검사
    private LocalTime reservationTime;
}
