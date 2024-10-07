package com.tablebookingservice.reservation.dto;

import com.tablebookingservice.reservation.type.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateReservation {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private ReservationStatus reservationStatus;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long reservationId;
        private String username;
        private String storeName;
        private ReservationStatus reservationStatus;

        private LocalDate reservationDate;
        private LocalTime reservationTime;

        public static Response of(ReservationDto reservation) {
            return Response.builder()
                    .reservationId(reservation.getReservationId())
                    .username(reservation.getUsername())
                    .storeName(reservation.getStoreName())
                    .reservationStatus(reservation.getReservationStatus())
                    .reservationDate(reservation.getReservationDate())
                    .reservationTime(reservation.getReservationTime())
                    .build();
        }
    }
}
