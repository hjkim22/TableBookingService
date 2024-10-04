package com.tablebookingservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;            // 고객 이름
    private String customerPhone;           // 고객 연락처
    private LocalDateTime reservationTime;  // 예약 시간
    private int numberOfGuests;             // 손님 수

    @ManyToOne // 여러 예약이 하나의 매장에 속할 수 있음
    @JoinColumn(name = "store_id")
    private Store store;
    private boolean isConfirmed;    // 예약 확인 여부
    private LocalDateTime createdAt = LocalDateTime.now();
}
