package com.tablebookingservice.reservation.entity;

import com.tablebookingservice.global.entity.GlobalEntity;
import com.tablebookingservice.reservation.type.ArrivalStatus;
import com.tablebookingservice.reservation.type.ReservationStatus;
import com.tablebookingservice.user.entity.UserEntity;
import jakarta.persistence.*;
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

//    @ManyToOne
//    @JoinColumn(name = "store_id")
//    private StoreEntity store;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus; // 승인 여부

    @Enumerated(EnumType.STRING)
    private ArrivalStatus arrivalStatus; // 도착 여부

    private LocalDate reservationDate;
    private LocalTime reservationTime;
}
