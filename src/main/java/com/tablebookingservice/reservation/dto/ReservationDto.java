package com.tablebookingservice.reservation.dto;

import com.tablebookingservice.reservation.entity.ReservationEntity;
import com.tablebookingservice.reservation.type.ArrivalStatus;
import com.tablebookingservice.reservation.type.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReservationDto {
    private Long reservationId;
    private String username;
    private String userPhoneNumber;
    private String storeName;

    private ReservationStatus reservationStatus;
    private ArrivalStatus arrivalStatus;

    private LocalDate reservationDate;
    private LocalTime reservationTime;

    public static ReservationDto fromEntity(ReservationEntity reservation) {
        return ReservationDto.builder()
                .reservationId(reservation.getId())
                .username(reservation.getUser().getUsername())
                .userPhoneNumber(reservation.getUser().getPhoneNumber())
                .storeName(reservation.getStore().getStoreName())
                .reservationStatus(reservation.getReservationStatus())
                .arrivalStatus(reservation.getArrivalStatus())
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .build();
    }
}

